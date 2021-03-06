package pl.grm.game.core.config;

/**
 * Parameters of game settings
 */
public class GameParameters {
	public static final String	APP_DATA				= System.getenv("APPDATA");
	public static final String	GAME_LOCATION			= APP_DATA + "\\GameBase\\";
	public static final String	LOG_FILE_NAME			= "Game.log";
	public static final String	GAME_TITLE				= "LWJGL Game Tut";
	public static final int		RENDER_QUEUE_CAPACITY	= 50;
	public static final int		FPS						= 60;
	public static final int		TPS						= 20;
	public static final String	CONFIG_FILE_NAME		= "Config.ini";
}
