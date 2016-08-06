package de.salocin.gl.util.render;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashSet;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import de.salocin.gl.render.Display;
import de.salocin.gl.render.Resolution;
import de.salocin.gl.render.gui.Gui;
import de.salocin.gl.util.font.Color;
import de.salocin.gl.util.texture.SimpleTexture;
import de.salocin.gl.util.texture.Texture;

public class TrueTypeFont implements Cloneable {
	
	private static final HashSet<TrueTypeFont> CACHE = new HashSet<TrueTypeFont>();
	private static final int CHAR_TEXTURE_PADDING = 10;
	private static final Graphics2D GLOBAL_GRAPHICS;
	
	/* Chars */
	private final char[] chars;
	
	/* Data */
	private TrueTypeFont.Data normalData = new TrueTypeFont.Data(Style.PLAIN);
	private TrueTypeFont.Data boldData = new TrueTypeFont.Data(Style.BOLD);
	private TrueTypeFont.Data italicData = new TrueTypeFont.Data(Style.ITALIC);
	private TrueTypeFont.Data boldItalicData = new TrueTypeFont.Data(Style.BOLD, Style.ITALIC);
	private TrueTypeFont.Data[] data = { normalData, boldData, italicData, boldItalicData };
	
	/* Settings */
	private final boolean antiAlias;
	private final int baseFontSize;
	private TrueTypeFont.Data currentData;
	private Style[] styles;
	private int size;
	
	static {
		GLOBAL_GRAPHICS = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1, 1, Transparency.TRANSLUCENT).createGraphics();
	}
	
	public static TrueTypeFont.Builder newBuilder(String fontName) {
		return new TrueTypeFont.Builder(fontName);
	}
	
	public static TrueTypeFont.Builder newBuilder(InputStream fontInputStream) throws FontFormatException, IOException {
		return new TrueTypeFont.Builder(fontInputStream);
	}
	
	public static TrueTypeFont.Builder newBuilder(File fontFile) throws FontFormatException, IOException {
		return new TrueTypeFont.Builder(fontFile);
	}
	
	/**
	 * <b>Only used for {@link #clone()}.
	 */
	private TrueTypeFont(int baseFontSize, boolean antiAlias, char[] chars) {
		this.antiAlias = antiAlias;
		this.baseFontSize = baseFontSize;
		this.chars = chars;
	}
	
	/**
	 * Only used for {@link Builder}.
	 */
	private TrueTypeFont(Font f, int baseFontSize, boolean antiAlias, char[] chars) {
		this.antiAlias = antiAlias;
		this.baseFontSize = baseFontSize;
		final Font base = f.deriveFont((float) baseFontSize);
		
		/* Create fonts */
		normalData.setFont(base);
		boldData.setFont(base.deriveFont(Font.BOLD));
		italicData.setFont(base.deriveFont(Font.ITALIC));
		boldItalicData.setFont(base.deriveFont(Font.BOLD | Font.ITALIC));
		
		/* Init */
		if (chars == null) {
			chars = new char[0];
		}
		this.chars = new char[256 + chars.length];
		initChars(chars);
		initFonts(antiAlias);
		
		/* Init settings */
		setSize(baseFontSize);
		setStyle();
	}
	
	private void initChars(char[] customChars) {
		for (int i = 0; i < chars.length; i++) {
			if (i < 256) {
				chars[i] = (char) i;
			} else {
				chars[i] = customChars[i - 256];
			}
		}
		
		for (Data d : data) {
			int total = 0;
			d.charPos = IntBuffer.allocate(chars.length);
			
			for (char c : chars) {
				d.charPos.put(total);
				total += d.fontMetrics.charWidth(c) + CHAR_TEXTURE_PADDING;
			}
			
			d.charPos.rewind();
		}
	}
	
	private void initFonts(boolean antiAlias) {
		new Exception().printStackTrace();
		for (TrueTypeFont.Data d : data) {
			final long start = System.currentTimeMillis();
			
			/* Create char texture */
			BufferedImage image = new BufferedImage(d.charPos.get(d.charPos.limit() - 1), d.font.getSize(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			if (antiAlias) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}
			g.setFont(d.font);
			
			/* Write chars */
			d.charPos.rewind();
			
			while (d.charPos.hasRemaining()) {
				g.drawString(String.valueOf(chars[d.charPos.position()]), d.charPos.get(), d.font.getSize() - d.fontMetrics.getDescent());
			}
			
			// DEBUG
			try {
				String style = "";
				
				for (Style s : d.style) {
					style += s.name();
				}
				
				File f = new File(d.font.getName() + "_" + style + ".png");
				f.delete();
				f.createNewFile();
				ImageIO.write(image, "png", f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/* Convert to OpenGL Texture */
			d.texture = new SimpleTexture(image);
			Display.logger.info("Font '" + d + "' created. Total time: " + (System.currentTimeMillis() - start) + "ms");
		}
	}
	
	private float getScale() {
		return (float) getSize() / (float) baseFontSize;
	}
	
	public boolean isAntiAliasEnabled() {
		return antiAlias;
	}
	
	public int getBaseFontSize() {
		return baseFontSize;
	}
	
	private void updateCurrentData() {
		for (TrueTypeFont.Data d : data) {
			if (d.is(styles)) {
				currentData = d;
			}
		}
	}
	
	public void setStyle(Style... styles) {
		if (normalData.is(styles)) {
			for (Style style : styles) {
				if (style.hasCustomFont() && style != Style.PLAIN) {
					throw new IllegalArgumentException("Can not combine '" + Style.PLAIN.name() + "' with '" + style.name() + "'");
				}
			}
		}
		
		this.styles = styles.length == 0 ? new Style[] { Style.PLAIN } : styles;
		
		updateCurrentData();
	}
	
	public Style[] getStyles() {
		return styles;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public float getFontHeight() {
		return Resolution.getInstance().getPixelInOrthoHeight((int) ((currentData.font.getSize() - currentData.fontMetrics.getDescent()) * getScale()));
	}
	
	public float getWidth(CharSequence text) {
		return Resolution.getInstance().getPixelInOrthoWidth((int) (currentData.fontMetrics.stringWidth(text.toString()) * getScale()));
	}
	
	public float getWidth(char c) {
		return getWidth(String.valueOf(c));
	}
	
	public float getWidth(char[] chars) {
		return getWidth(new String(chars));
	}
	
	public void renderText(CharSequence text, float x, float y) {
		renderText(text, x, y, Color.white);
	}
	
	public void renderText(CharSequence text, float x, float y, Color color) {
		renderText(text, x, y, color, AlginH.LEFT);
	}
	
	public void renderText(CharSequence text, float x, float y, Color color, AlginH alginH) {
		renderText(text, x, y, color, alginH, true);
	}
	
	public void renderText(CharSequence text, float x, float y, Color color, AlginH alginH, boolean enableAlpha) {
		if (currentData.texture == null) {
			// Font texture still loading and not available
			return;
		}
		
		float xOffset = 0;
		
		if (enableAlpha) {
			Gui.enableAlpha();
		}
		
		color.bind();
		
		final float scale = getScale();
		GL11.glPushMatrix();
		GL11.glTranslatef(x - (x * scale), y - (y * scale), 0.0f);
		GL11.glScalef(scale, scale, 0.0f);
		
		if (alginH == AlginH.RIGHT) {
			for (int i = text.length() - 1; i >= 0; i--) {
				final char c = text.charAt(i);
				xOffset -= getWidth(c);
				renderChar(c, x + xOffset, y);
			}
		} else {
			for (int i = 0; i < text.length(); i++) {
				final char c = text.charAt(i);
				renderChar(c, x + xOffset, y);
				xOffset += getWidth(c);
			}
		}
		
		GL11.glPopMatrix();
		
		if (enableAlpha) {
			Gui.disableAlpha();
		}
	}
	
	private void renderChar(char c, float x, float y) {
		final float charWidth = getWidth(c);
		final float lineHeight = getFontHeight();
		
		int charPos = (int) c < 256 ? (int) c : -1;
		
		if (charPos == -1) {
			try {
				currentData.charPos.position(256);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Char '" + c + "' is not registered for this TrueTypeFont.", e);
			}
			
			while (currentData.charPos.hasRemaining()) {
				if (chars[currentData.charPos.position()] == c) {
					charPos = currentData.charPos.position();
					break;
				}
			}
			
			if (charPos == -1) {
				throw new RuntimeException("Char '" + c + "' is not registered for this TrueTypeFont.");
			}
		}
		
		currentData.texture.render(x, y, charWidth, lineHeight, currentData.charPos.get(charPos), 0.0f, currentData.fontMetrics.charWidth(c), currentData.texture.getHeight());
	}
	
	@Override
	public TrueTypeFont clone() {
		TrueTypeFont font = new TrueTypeFont(baseFontSize, antiAlias, Arrays.copyOfRange(chars, 256, chars.length));
		font.normalData = normalData.clone();
		font.boldData = boldData.clone();
		font.italicData = italicData.clone();
		font.boldItalicData = boldItalicData.clone();
		font.size = size;
		font.styles = styles;
		font.currentData = currentData;
		return font;
	}
	
	private class Data implements Cloneable {
		
		private Style[] style;
		private Font font;
		private FontMetrics fontMetrics;
		private IntBuffer charPos;
		private Texture texture;
		
		private Data(Style... styles) {
			this.style = styles;
		}
		
		private void setFont(Font font) {
			this.font = font;
			this.fontMetrics = GLOBAL_GRAPHICS.getFontMetrics(font);
		}
		
		private boolean is(Style... styles) {
			OUTER: for (Style style : styles) {
				if (style.hasCustomFont()) {
					for (Style s : this.style) {
						if (style == s) {
							continue OUTER;
						}
					}
					
					return false;
				}
			}
			
			return true;
		}
		
		@Override
		protected Data clone() {
			Data d = new Data(style);
			d.font = font;
			d.fontMetrics = fontMetrics;
			d.charPos = charPos;
			d.texture = texture;
			return d;
		}
		
		@Override
		public String toString() {
			return font.getFontName();
		}
		
	}
	
	public static class Builder {
		
		private Font baseFont;
		private int fontBaseSize = 50;
		private char[] chustomChars = new char[0];
		private int fontSize = fontBaseSize;
		private Style[] fontStyle = new Style[0];
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
		public Builder(File file) throws FontFormatException, IOException {
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
		public Builder(InputStream inputStream) throws FontFormatException, IOException {
			this.baseFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		}
		
		/**
		 * Searches the system for the given Font name and renders the text with
		 * the found font.
		 * 
		 * @param fontName
		 *            The Font name
		 */
		public Builder(String fontName) {
			this.baseFont = new Font(fontName, Font.PLAIN, 1);
		}
		
		/**
		 * Set the base font size. This size will be scaled when changing the
		 * {@link TrueTypeFont} size ({@link TrueTypeFont#setSize(int)}).<br>
		 * As a result, the higher the number the more detailed the final Font
		 * will be.<br>
		 * This option is optional. The default value is 50.
		 * 
		 * @param fontSize
		 *            The font size
		 * @return Current {@link Builder} instance
		 */
		public Builder setBaseFontSize(int fontSize) {
			this.fontBaseSize = fontSize;
			return this;
		}
		
		/**
		 * This allows to add chars that are not supported by the ASCII
		 * code.<br>
		 * This option is optional.
		 * 
		 * @param chustomChars
		 *            The chars to add.
		 * @return Current {@link Builder} instance
		 */
		public Builder setChustomChars(char... chustomChars) {
			this.chustomChars = chustomChars;
			return this;
		}
		
		/**
		 * Sets the real font size. The {@link #setBaseFontSize(int)} will be
		 * scaled to match this value.<br>
		 * <b>This option can be changed in the <code>TrueTypeFont</code>
		 * instance.</b>
		 * 
		 * @param fontSize
		 *            The font size
		 * @return Current {@link Builder} instance
		 * @see TrueTypeFont#setSize(int)
		 */
		public Builder setFontSize(int fontSize) {
			this.fontSize = fontSize;
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
		public Builder setFontStyle(Style... fontStyle) {
			this.fontStyle = fontStyle;
			return this;
		}
		
		/**
		 * Enables Anti-Aliasing for the font. <br>
		 * This option is optional. The default value is set to
		 * <code>true</code>.
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
		public TrueTypeFont build() {
			TrueTypeFont font = new TrueTypeFont(baseFont, fontBaseSize, antiAlias, chustomChars);
			font.setSize(fontSize);
			font.setStyle(fontStyle);
			return font;
		}
		
	}
	
}
