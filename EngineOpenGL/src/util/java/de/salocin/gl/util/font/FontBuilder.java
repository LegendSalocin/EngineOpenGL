package de.salocin.gl.util.font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang3.Validate;

/**
 * With this class it is very easy to create new fonts.<br>
 * <b>This class will only build {@link TrueTypeFont}s!</b>
 * 
 * @see #build()
 */
public class FontBuilder {
	
	private String fontFamily;
	private FontStyle fontStyle;
	private String fullFontName;
	private char[] chustomChars = new char[0];
	private int fontSize = 20;
	private boolean underline;
	private boolean strikethrough;
	private boolean overline;
	
	// TODO
	// private boolean antiAlias = true;
	
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
	 * Changes the font family to the given value. If the fontFamily is not
	 * supported, an {@link IllegalArgumentException} will be thrown.
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
	 * thrown.
	 * 
	 * @param fontStyle
	 *            The new font style
	 * @return The current instance
	 * @throws IllegalArgumentException
	 *             if the font style is not supported
	 */
	public FontBuilder setFontStyle(FontStyle fontStyle) {
		Validate.notNull(fontStyle);
		
		String fullName = fontFamily + fontStyle.getFontSuffix();
		
		if (!SupportedFonts.isFontSupported(fullName)) {
			throw new IllegalArgumentException("This font style is not supported for '" + fontFamily + "': " + fontStyle.name());
		}
		
		this.fontStyle = fontStyle;
		this.fullFontName = fullName;
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
	 * Enables Anti-Aliasing for the font. <br>
	 * This option is optional. The default value is set to <code>true</code>.
	 * 
	 * @param antiAlias
	 *            <code>true</code> to enable Anti-Aliasing, otherwise
	 *            <code>false</code>.
	 * @return The current instance
	 */
	// TODO
	@Deprecated
	public FontBuilder setAntiAlias(boolean antiAlias) {
		// this.antiAlias = antiAlias;
		return this;
	}
	
	/**
	 * Creates the new {@link TrueTypeFont} instance based on the settings.
	 * 
	 * @return A new {@link TrueTypeFont}
	 */
	public Font build() {
		InputStream fontInputStream;
		
		try {
			if (fontFamily == null) {
				throw new NullPointerException("fontFamily == null");
			}
			
			fontInputStream = new FileInputStream(SupportedFonts.getSupportedFont(fullFontName).path);
		} catch (NullPointerException | FileNotFoundException e) {
			throw new RuntimeException("This exception should not be thrown. Please send a bug report.", e);
		}
		
		Font font = new TrueTypeFont(this, fontInputStream, fontStyle, fontSize, chustomChars);
		font.setUnderline(underline);
		font.setStrikethrough(strikethrough);
		font.setOverline(overline);
		return font;
	}
}
