package de.salocin.gl.util.font;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import de.salocin.gl.render.gui.Gui;
import de.salocin.gl.util.Color;
import de.salocin.gl.util.texture.SimpleTexture;

public class TrueTypeFontRenderer implements Cloneable {
	
	protected static final Graphics2D GLOBAL_GRAPHICS;
	
	static {
		GLOBAL_GRAPHICS = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1, 1, Transparency.TRANSLUCENT).createGraphics();
	}
	
	private final TrueTypeFont font;
	
	private TrueTypeFontData normalData = new TrueTypeFontData(FontStyle.PLAIN);
	private TrueTypeFontData boldData = new TrueTypeFontData(FontStyle.BOLD);
	private TrueTypeFontData italicData = new TrueTypeFontData(FontStyle.ITALIC);
	private TrueTypeFontData boldItalicData = new TrueTypeFontData(FontStyle.BOLD, FontStyle.ITALIC);
	private TrueTypeFontData[] allData = { normalData, boldData, italicData, boldItalicData };
	private TrueTypeFontData currentData = null;
	
	private char[] chars;
	
	/**
	 * 
	 */
	private TrueTypeFontRenderer(TrueTypeFont font) {
		this.font = font;
	}
	
	protected TrueTypeFontRenderer(TrueTypeFont font, Font baseFont, char[] chars) {
		this(font);
		
		initChars(chars);
		initData(baseFont.deriveFont((float) font.metrics.getBaseSize()));
	}
	
	protected void initChars(char[] chars) {
		this.chars = new char[256 + chars.length];
		
		for (int i = 0; i < allData.length; i++) {
			if (i < 256) {
				this.chars[i] = (char) i;
			} else {
				this.chars[i] = chars[i - 256];
			}
		}
	}
	
	protected void initData(Font base) {
		normalData.setFont(base);
		boldData.setFont(base.deriveFont(Font.BOLD));
		italicData.setFont(base.deriveFont(Font.ITALIC));
		boldItalicData.setFont(base.deriveFont(Font.BOLD | Font.ITALIC));
		
		for (TrueTypeFontData d : allData) {
			/* Create char texture */
			BufferedImage image = new BufferedImage(d.charPos.get(d.charPos.limit() - 1), d.font.getSize(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			if (font.isAntiAliasEnabled()) {
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
				
				for (FontStyle s : d.style) {
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
		}
	}
	
	protected void renderText(CharSequence text, float x, float y, Color color) {
		if (currentData == null || currentData.texture == null) {
			// Font texture still loading and not available
			return;
		}
		
		float xOffset = 0;
		
		Gui.enableAlpha();
		color.bind();
		
		final float scale = getCurrentScale();
		GL11.glPushMatrix();
		GL11.glTranslatef(x - (x * scale), y - (y * scale), 0.0f);
		GL11.glScalef(scale, scale, 0.0f);
		
		for (int i = 0; i < text.length(); i++) {
			final char c = text.charAt(i);
			renderChar(c, x + xOffset, y);
			xOffset += font.metrics.getWidth(c);
		}
		
		GL11.glPopMatrix();
		Gui.disableAlpha();
	}
	
	protected void renderChar(char c, float x, float y) {
		final float charWidth = font.metrics.getWidth(c);
		final float lineHeight = font.metrics.getSize();
		
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
	
	protected void updateStyle(FontStyle[] styles) {
		if (normalData.is(styles)) {
			for (FontStyle style : styles) {
				if (style.customFont && style != FontStyle.PLAIN) {
					throw new IllegalArgumentException("Can not combine '" + FontStyle.PLAIN.name() + "' with '" + style.name() + "'");
				}
			}
		}
		
		for (TrueTypeFontData d : allData) {
			if (d.is(styles)) {
				currentData = d;
			}
		}
		
		currentData = null;
	}
	
	protected float getCurrentScale() {
		return (float) font.metrics.getSize() / (float) font.metrics.getBaseSize();
	}
	
	@Override
	protected TrueTypeFontRenderer clone() {
		TrueTypeFontRenderer r = new TrueTypeFontRenderer(font);
		r.normalData = normalData;
		r.boldData = boldData;
		r.italicData = italicData;
		r.boldItalicData = boldItalicData;
		r.chars = chars;
		return r;
	}
	
}
