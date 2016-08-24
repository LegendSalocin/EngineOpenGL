package de.salocin.engine.utils.font;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.utils.core.ResourceLocation;
import de.salocin.engine.utils.font.truetype.TrueTypeFont;

/**
 * With this class it is very easy to create new fonts.<br>
 * <b>This class will only build {@link TrueTypeFont}s!</b>
 * 
 * @see #build()
 */
public class FontBuilder {
	
	private ResourceLocation customFont;
	private String fontFamily;
	private FontStyle fontStyle;
	private String fullFontName;
	private char[] chustomChars = new char[0];
	private int fontSize = 20;
	private boolean underline;
	private boolean strikethrough;
	private boolean overline;
	
	/**
	 * Creates a new {@link FontBuilder} using the specified input data using
	 * the found font input data of the current system.
	 * 
	 * @param fontFamily
	 *            The font family name
	 * @throws IllegalArgumentException
	 *             if the font does not exist or is not supported.
	 * @see SystemFontLocation#isFontSupported(String)
	 * @see SystemFontLocation#getSupportedFont(String)
	 * @see #setFontFamily(String)
	 */
	public FontBuilder(String fontFamily) {
		this(fontFamily, FontStyle.PLAIN);
	}
	
	/**
	 * Creates a new {@link FontBuilder} using the specified input data using
	 * the found font input data of the current system.
	 * 
	 * @param fontFamily
	 *            The font family name
	 * @param fontStyle
	 *            The font style
	 * @throws IllegalArgumentException
	 *             if the font or the font style are not supported
	 * @see SystemFontLocation#isFontSupported(String)
	 * @see SystemFontLocation#getSupportedFont(String)
	 * @see #setFontFamily(String)
	 * @see #setFontStyle(FontStyle)
	 */
	public FontBuilder(String fontFamily, FontStyle fontStyle) {
		setFontFamily(fontFamily);
		setFontStyle(fontStyle);
	}
	
	/**
	 * Creates a new {@link FontBuilder} using the specified input data using
	 * the found font input data of the current system.
	 * 
	 * @param customFontResource
	 *            The font's location
	 */
	public FontBuilder(ResourceLocation customFontResource) {
		setCustomFontResource(customFontResource);
	}
	
	/**
	 * Changes the font family to the given value. If the fontFamily is not
	 * supported, an {@link IllegalArgumentException} will be thrown.<br>
	 * This will reset the custom font resource location.
	 * 
	 * @param fontFamily
	 * @return The current instance
	 * @throws IllegalArgumentException
	 *             if the font family is not supported
	 */
	public FontBuilder setFontFamily(String fontFamily) {
		Validate.notNull(fontFamily);
		
		if (!SupportedFonts.isFontSupported(fontFamily)) {
			throw new IllegalArgumentException("This font family is not supported: " + fontFamily);
		}
		
		this.customFont = null;
		this.fontFamily = fontFamily;
		
		if (fontStyle == null) {
			fontStyle = FontStyle.PLAIN;
		}
		
		setFontStyle(fontStyle);
		
		return this;
	}
	
	/**
	 * Changes the font style to the given value. If the current font family
	 * does not support this style, an {@link IllegalArgumentException} will be
	 * thrown.<br>
	 * This will reset the custom font resource location.
	 * 
	 * @param fontStyle
	 *            The new font style
	 * @return The current instance
	 * @throws IllegalArgumentException
	 *             if the font style is not supported
	 */
	public FontBuilder setFontStyle(FontStyle fontStyle) {
		Validate.notNull(fontStyle);
		
		if (fontFamily == null) {
			throw new RuntimeException("Set the font family first");
		}
		
		String fullName = fontFamily + fontStyle.getFontFamilySuffix();
		
		if (!SupportedFonts.isFontSupported(fullName)) {
			throw new IllegalArgumentException("This font style is not supported for '" + fontFamily + "': " + fontStyle.name());
		}
		
		this.customFont = null;
		this.fontStyle = fontStyle;
		this.fullFontName = fullName;
		return this;
	}
	
	/**
	 * Changes the custom font resource location. This will automatically reset
	 * the set font family and the font style.
	 * 
	 * @param fontLocation
	 *            The font's location
	 * @return The current instance
	 */
	public FontBuilder setCustomFontResource(ResourceLocation fontLocation) {
		Validate.notNull(fontLocation);
		this.customFont = fontLocation;
		this.fontFamily = null;
		this.fontStyle = null;
		this.fullFontName = null;
		return this;
	}
	
	/**
	 * This allows to add chars that are not supported by the ASCII code.<br>
	 * This option is optional.
	 * 
	 * @param chustomChars
	 *            The chars to add.
	 * @return The current instance
	 */
	public FontBuilder setChustomChars(char... chustomChars) {
		this.chustomChars = chustomChars;
		return this;
	}
	
	/**
	 * Sets the font size (in pixel). <b>This option can be changed in the
	 * <code>TrueTypeFont</code> instance.</b>
	 * 
	 * @param fontSizeInPixel
	 *            The font size
	 * @return The current instance
	 */
	public FontBuilder setFontSize(int fontSizeInPixel) {
		this.fontSize = fontSizeInPixel;
		return this;
	}
	
	public FontBuilder setUnderline(boolean underline) {
		this.underline = underline;
		return this;
	}
	
	public FontBuilder setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
		return this;
	}
	
	public FontBuilder setOverline(boolean overline) {
		this.overline = overline;
		return this;
	}
	
	/**
	 * Creates the new {@link TrueTypeFont} instance based on the settings.
	 * 
	 * @return A new {@link TrueTypeFont}
	 */
	public Font build() {
		InputStream fontInputStream;
		Font font;
		
		try {
			if (customFont == null) {
				if (fontFamily == null) {
					throw new RuntimeException(new NullPointerException("fontFamily & customFont = null"));
				}
				
				fontInputStream = new FileInputStream(SupportedFonts.getSupportedFont(fullFontName).path);
			} else {
				fontInputStream = customFont.openStream();
			}
			
			font = new TrueTypeFont(this, fontInputStream, fontStyle, fontSize, chustomChars);
			font.setUnderline(underline);
			font.setStrikethrough(strikethrough);
			font.setOverline(overline);
		} catch (Exception e) {
			throw new RuntimeException("This exception should not be thrown. Please send a bug report.", e);
		}
		return font;
	}
}
