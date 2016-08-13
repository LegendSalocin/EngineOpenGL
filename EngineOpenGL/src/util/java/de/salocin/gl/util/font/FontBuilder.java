package de.salocin.gl.util.font;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * With this class it is very easy to create new fonts.<br>
 * <b>This class will only build {@link TrueTypeFont}s!</b>
 * 
 * @see #build()
 */
public class FontBuilder {
	
	private InputStream ttf;
	private char[] chustomChars = new char[0];
	private int fontSize = 20;
	private FontStyle[] fontStyle = new FontStyle[0];
	
	// TODO
	// private boolean antiAlias = true;
	
	/**
	 * Creates a new {@link TrueTypeFont} using the specified font type and
	 * input data.
	 * 
	 * @param inputStream
	 *            The {@link InputStream} of the font
	 * @throws FontFormatException
	 *             if the fontStream data does not contain the required font
	 *             tables for the specified format.
	 * @throws IOException
	 *             if the <code>fontStream</code> cannot be completely read.
	 */
	public FontBuilder(File file) throws FontFormatException, IOException {
		this(new FileInputStream(file));
	}
	
	/**
	 * Creates a new {@link TrueTypeFont} using the specified font type and
	 * input data.
	 * 
	 * @param inputStream
	 *            The {@link InputStream} of the font
	 * @throws FontFormatException
	 *             if the fontStream data does not contain the required font
	 *             tables for the specified format.
	 * @throws IOException
	 *             if the <code>fontStream</code> cannot be completely read.
	 */
	public FontBuilder(InputStream inputStream) throws FontFormatException, IOException {
		this.ttf = inputStream;
	}
	
	/**
	 * Searches the system for the given font name and renders the text with the
	 * found font.
	 * 
	 * @param fontFamily
	 *            The font family name
	 * @throws IllegalArgumentException
	 *             if the font does not exist or is not supported.
	 * @see SystemFontLocation#isFontSupported(String)
	 * @see SystemFontLocation#getSupportedFont(String)
	 */
	public FontBuilder(String fontFamily) {
		if (!SupportedFonts.isFontSupported(fontFamily)) {
			throw new IllegalArgumentException("This font is not supported: " + fontFamily);
		}
		
		try {
			this.ttf = new FileInputStream(SupportedFonts.getSupportedFont(fontFamily).path);
		} catch (FileNotFoundException e) {
			// Should never be thrown
			throw new IllegalArgumentException("Font file could not be found", e);
		}
	}
	
	/**
	 * Uses the settings in the given font argument.
	 * 
	 * @param font
	 *            The font to copy from
	 * @throws IllegalArgumentException
	 *             if the font is not an instance of {@link TrueTypeFont}
	 */
	public FontBuilder(Font font) {
		if (!(font instanceof TrueTypeFont)) {
			throw new IllegalArgumentException("The font is not an instance of TrueTypeFont");
		}
		
		TrueTypeFont ttf = (TrueTypeFont) font;
		this.ttf = ttf.ttf;
		this.chustomChars = ttf.customChars;
		this.fontSize = ttf.getSize();
		this.fontStyle = ttf.getStyle();
	}
	
	/**
	 * This allows to add chars that are not supported by the ASCII code.<br>
	 * This option is optional.
	 * 
	 * @param chustomChars
	 *            The chars to add.
	 * @return Current {@link Builder} instance
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
	 * @return Current {@link Builder} instance
	 * @see TrueTypeFont#setSize(int)
	 */
	public FontBuilder setFontSize(int fontSizeInPixel) {
		this.fontSize = fontSizeInPixel;
		return this;
	}
	
	/**
	 * Sets the font style.<br>
	 * <b>This option can be changed in the <code>TrueTypeFont</code>
	 * instance.</b>
	 * 
	 * @param fontStyle
	 *            The font style
	 * @return Current {@link Builder} instance
	 * @see TrueTypeFont#setStyle(Style...)
	 */
	public FontBuilder setFontStyle(FontStyle... fontStyle) {
		this.fontStyle = fontStyle;
		return this;
	}
	
	/**
	 * Enables Anti-Aliasing for the font. <br>
	 * This option is optional. The default value is set to <code>true</code>.
	 * 
	 * @param antiAlias
	 *            <code>true</code> to enable Anti-Aliasing, otherwise
	 *            <code>false</code>.
	 */
	// TODO
	@Deprecated
	public void setAntiAlias(boolean antiAlias) {
		// this.antiAlias = antiAlias;
	}
	
	/**
	 * Creates the new {@link TrueTypeFont} instance based on the settings.
	 * 
	 * @return A new {@link TrueTypeFont}
	 */
	public Font build() {
		Font font = new TrueTypeFont(ttf, fontSize, chustomChars);
		font.setStyle(fontStyle);
		return font;
	}
}
