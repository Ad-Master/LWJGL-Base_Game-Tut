package pl.grm.game.core.misc;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class Fonts {
	private static ArrayList<TrueTypeFont>	fonts;
	
	private static void init() throws FontFormatException, IOException {
		fonts = new ArrayList<TrueTypeFont>();
		fonts.add(new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), false));
		InputStream inputStream = ResourceLoader.getResourceAsStream("arial.ttf");
		;
		fonts.add(new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, inputStream)
				.deriveFont(24f), false));
	}
	
	public static TrueTypeFont getFont(int i) {
		if (fonts == null) {
			try {
				init();
			}
			catch (FontFormatException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fonts.size() > i) { return fonts.get(i); }
		return new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), false);
	}
}
