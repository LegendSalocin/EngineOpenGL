package de.salocin.engine.utils.font;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.salocin.gl.util.exception.EngineException;

/**
 * A class to retrieve all supported fonts by the current OS.
 * 
 * Currently only TrueTypeFonts are supported.
 */
public class SupportedFonts {
	
	private static final ArrayList<SupportedFont> supportedFonts = new ArrayList<SupportedFont>();
	
	static {
		java.awt.Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		String[] fontNames = new String[fonts.length];
		
		for (int i = 0; i < fontNames.length; i++) {
			fontNames[i] = fonts[i].getFontName(Locale.ENGLISH);
		}
		
		try {
			final Object factory = Class.forName("sun.font.FontManagerFactory").getMethod("getInstance").invoke(null);
			final Method factoryFindMethod = factory.getClass().getMethod("findFont2D", String.class, int.class, int.class);
			
			for (String fontName : fontNames) {
				final Object font2D = factoryFindMethod.invoke(factory, fontName, 0, 0);
				
				if (font2D != null & Class.forName("sun.font.PhysicalFont").isAssignableFrom(font2D.getClass())) {
					supportedFonts.add(new SupportedFont(font2D));
				}
			}
		} catch (Exception e) {
			new EngineException("Could not load system fonts.", e).log();
		}
	}
	
	/**
	 * Returns all supported TrueTypeFonts.
	 * 
	 * @return A list of all supported fonts
	 */
	public static List<SupportedFont> getSupportedFonts() {
		return supportedFonts;
	}
	
	/**
	 * 
	 * 
	 * @param fullFontName
	 * @return
	 */
	public static boolean isFontSupported(String fullFontName) {
		return getSupportedFont(fullFontName) != null;
	}
	
	/**
	 * 
	 * @param fullFontName
	 * @return
	 */
	public static SupportedFont getSupportedFont(String fullFontName) {
		for (SupportedFont supportedFont : supportedFonts) {
			if (supportedFont.fullName.equalsIgnoreCase(fullFontName)) {
				return supportedFont;
			}
		}
		
		return null;
	}
	
	/**
	 * A wrapper method to retrieve a font's path
	 */
	public static class SupportedFont {
		public final String family;
		public final String fullName;
		public final String path;
		
		private SupportedFont(String family, String fullName, String path) {
			this.family = family;
			this.fullName = fullName;
			this.path = path;
		}
		
		private SupportedFont(Object font2D) throws Exception {
			final Class<?> physicalFont = Class.forName("sun.font.PhysicalFont");
			
			family = physicalFont.getMethod("getFamilyName", Locale.class).invoke(font2D, Locale.ENGLISH).toString();
			fullName = physicalFont.getMethod("getFontName", Locale.class).invoke(font2D, Locale.ENGLISH).toString();
			
			final Field platNameField = physicalFont.getDeclaredField("platName");
			platNameField.setAccessible(true);
			path = platNameField.get(font2D).toString();
		}
	}
	
}
