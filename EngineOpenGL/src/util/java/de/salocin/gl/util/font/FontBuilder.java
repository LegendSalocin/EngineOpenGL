package de.salocin.gl.util.font;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FontBuilder {
	
	private java.awt.Font baseFont;
	private int fontBaseSize = 50;
	private char[] chustomChars = new char[0];
	private int fontSize = fontBaseSize;
	private FontStyle[] fontStyle = new FontStyle[0];
	private boolean antiAlias = true;
	
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
		this.baseFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
	}
	
	/**
	 * Searches the system for the given Font name and renders the text with the
	 * found font.
	 * 
	 * @param fontName
	 *            The Font name
	 */
	public FontBuilder(String fontName) {
		this.baseFont = new java.awt.Font(fontName, java.awt.Font.PLAIN, 1);
	}
	
	/**
	 * Set the base font size. This size will be scaled when changing the
	 * {@link TrueTypeFont} size ({@link TrueTypeFont#setSize(int)}).<br>
	 * As a result, the higher the number the more detailed the final Font will
	 * be.<br>
	 * This option is optional. The default value is 50.
	 * 
	 * @param fontSize
	 *            The font size
	 * @return Current {@link Builder} instance
	 */
	public FontBuilder setBaseFontSize(int fontSize) {
		this.fontBaseSize = fontSize;
		return this;
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
	 * Sets the real font size (in pixel). The {@link #setBaseFontSize(int)}
	 * will be scaled to match this value.<br>
	 * <b>This option can be changed in the <code>TrueTypeFont</code>
	 * instance.</b>
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
	 * Sets the font style. <br>
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
	public void setAntiAlias(boolean antiAlias) {
		this.antiAlias = antiAlias;
	}
	
	/**
	 * Creates the new {@link TrueTypeFont} instance based on the settings.
	 * 
	 * @return A new {@link TrueTypeFont}
	 */
	public Font build() {
		Font font = new TrueTypeFont(baseFont, fontBaseSize, antiAlias, chustomChars);
		font.setSize(fontSize);
		font.setStyle(fontStyle);
		return font;
	}
}
