package de.salocin.gl.util.render;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.render.Resolution;
import de.salocin.gl.render.SimpleTexture;
import de.salocin.gl.render.Texture;
import de.salocin.gl.render.gui.Gui;
import de.salocin.gl.util.exception.DetailedException;

public class TrueTypeFont {
	
	private static final HashSet<TrueTypeFont> cache = new HashSet<TrueTypeFont>();
	private static final int correctionWidth = 10;
	private static final GraphicsConfiguration graphicsConfiguration;
	private static final Graphics2D fontMetricsGraphics;
	private Texture fontTexture;
	private Font font;
	private FontMetrics fontMetrics;
	private char[] chars;
	private int charsTextureWidth;
	private int charsTextureHeight;
	
	static {
		graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		fontMetricsGraphics = graphicsConfiguration.createCompatibleImage(1, 1, Transparency.TRANSLUCENT).createGraphics();
	}
	
	public TrueTypeFont(File file, int size) throws FontFormatException, IOException {
		this(new FileInputStream(file), size);
	}
	
	public TrueTypeFont(InputStream inputStream, int size) throws FontFormatException, IOException {
		this(Font.createFont(Font.TRUETYPE_FONT, inputStream));
		setFontSize(size);
	}
	
	public TrueTypeFont(String name, int size) {
		this(new Font(name, Font.PLAIN, size));
	}
	
	public TrueTypeFont(Font font) {
		this(font, true);
	}
	
	public TrueTypeFont(Font font, boolean antiAlias) {
		this(font, antiAlias, null);
	}
	
	public TrueTypeFont(Font font, boolean antiAlias, char[] customChars) {
		Validate.notNull(font);
		this.font = font;
		// TODO
		// this.antiAlias = antiAlias;
		setCustomChars(customChars);
	}
	
	protected void updateFont() {
		for (TrueTypeFont f : cache) {
			if (f.font.equals(font)) {
				fontMetrics = f.fontMetrics;
				charsTextureWidth = f.charsTextureWidth;
				charsTextureHeight = f.charsTextureHeight;
				fontTexture = f.fontTexture;
				return;
			}
		}
		
		cache.add(this);
		
		fontMetrics = fontMetricsGraphics.getFontMetrics(font);
		
		charsTextureWidth = 0;
		
		for (char c : chars) {
			charsTextureWidth += fontMetrics.charWidth(c);
		}
		
		if (charsTextureWidth == 0) {
			throw new RuntimeException("charsTextureWidth is 0", new DetailedException().addDetail("Font Name", font.getName()).addDetail("Chars Length", chars.length));
		}
		
		charsTextureHeight = fontMetrics.getHeight();
		
		BufferedImage image = new BufferedImage(charsTextureWidth, charsTextureHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setFont(font);
		
		int xOffset = 0;
		int y = font.getSize();
		
		for (char c : chars) {
			g.drawString(String.valueOf(c), xOffset, y);
			xOffset += fontMetrics.charWidth(c) + correctionWidth;
		}
		
		fontTexture = new SimpleTexture(image);
	}
	
	public void setCustomChars(char[] customChars) {
		if (customChars == null || customChars.length == 0) {
			chars = new char[256];
		} else {
			chars = new char[256 + customChars.length];
		}
		
		for (int i = 0; i < chars.length; i++) {
			if (i < 256) {
				chars[i] = (char) i;
			} else {
				chars[i] = customChars[i - 256];
			}
		}
		
		updateFont();
	}
	
	public void setFontSize(int size) {
		font = font.deriveFont(size);
		updateFont();
	}
	
	public int getFontSize() {
		return font.getSize();
	}
	
	public float getLineHeight() {
		return Resolution.getInstance().getPixelInOrthoHeight(fontMetrics.getHeight());
	}
	
	public float getWidth(CharSequence text) {
		return Resolution.getInstance().getPixelInOrthoWidth(fontMetrics.stringWidth(text.toString()));
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
		float xOffset = 0;
		
		if (enableAlpha) {
			Gui.enableAlpha();
		}
		
		color.bind();
		
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
		
		if (enableAlpha) {
			Gui.disableAlpha();
		}
	}
	
	private void renderChar(char c, float x, float y) {
		float textureX = 0.0f;
		float textureCharWidth = fontMetrics.charWidth(c);
		float textureCharHeight = charsTextureHeight;
		float charWidth = getWidth(c);
		float lineHeight = getLineHeight();
		
		for (char ch : chars) {
			if (ch == c) {
				break;
			} else {
				textureX += fontMetrics.charWidth(ch) + correctionWidth;
			}
		}
		
		fontTexture.render(x, y, charWidth, lineHeight, textureX, 0.0f, textureCharWidth, textureCharHeight);
	}
	
}
