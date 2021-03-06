package pl.grm.game.core.threads;

import static org.lwjgl.opengl.GL11.*;

import java.util.*;
import java.util.logging.*;

import org.lwjgl.*;
import org.lwjgl.opengl.*;

import pl.grm.game.core.*;
import pl.grm.game.core.config.*;
import pl.grm.game.core.entities.*;
import pl.grm.game.core.loadstages.*;
import pl.grm.game.core.misc.*;
import pl.grm.game.core.misc.timers.*;

import com.google.common.collect.*;

public class RenderThread extends Thread {
	private FPSTimer					timer;
	private Multimap<Integer, Entity>	entities;
	
	public RenderThread() {
		super(GameParameters.GAME_TITLE + " Game-Render-Thread");
		this.timer = GameController.instance.getFPSTimer();
	}
	
	@Override
	public void run() {
		initRenderer();
		while (GameController.instance.isRunning()) {
			if (Display.isCloseRequested()) {
				GameController.stopGame();
			}
			loop();
		}
		Display.destroy();
	}
	
	/**
	 * Called before loop
	 */
	private void initRenderer() {
		this.timer.initTime();
		this.entities = GameController.instance.getGame().getEntities();
		try {
			Display.create();
			if (!GLContext.getCapabilities().OpenGL11) {
				System.err
						.println("Your OpenGL version doesn't support the required functionality.");
				GameController.stopGame();
			}
		}
		catch (LWJGLException e) {
			GameLogger.log(Level.SEVERE, e.toString(), e);
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glScalef(1.0f, 1.0f, 1.0f);
		glOrtho(0, 800, 600, 0, 1, -1);
		glFrustum(-1, 1, -1, 1, 0.0, 40.0);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glShadeModel(GL_SMOOTH);
	}
	
	private void loop() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		switch (GameController.getGameStage()) {
			case INTRO :
			case MAIN_MENU :
			case GAME_LOADING :
				LoadGameStage.renderStage();
				break;
			case GAME :
				renderEntities();
				break;
			default :
				break;
		}
		Display.update();
		timer.updateFPS();
		Display.sync(GameParameters.FPS);
	}
	
	private void renderEntities() {
		Set<Integer> keySet = entities.keySet();
		Iterator<Integer> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
			Integer key = keySetIterator.next();
			Collection<Entity> entityCollection = entities.get(key);
			Iterator<Entity> entityCollectionIterator = entityCollection.iterator();
			synchronized (entityCollectionIterator) {
				while (entityCollectionIterator.hasNext()) {
					Entity entity = entityCollectionIterator.next();
					entity.render();
				}
			}
		}
	}
}
