package de.salocin.gl.util.font;

import static org.lwjgl.BufferUtils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.commons.lang3.Validate;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;

import de.salocin.gl.util.Color;

/**
 * Not part of the official API
 */
public class TrueTypeFontRenderer {
	
	protected static final int BITMAP_WIDTH = 512;
	protected static final int BITMAP_HEIGHT = 512;
	
	protected final TrueTypeFont font;
	protected final char[] usedChars;
	
	protected final FloatBuffer xPos = MemoryUtil.memAllocFloat(1);
	protected final FloatBuffer yPos = MemoryUtil.memAllocFloat(1);
	
	protected STBTTFontinfo fontInfo;
	protected STBTTPackedchar.Buffer chardata;
	protected STBTTAlignedQuad quad = STBTTAlignedQuad.malloc();
	
	protected float textWidth;
	protected float textX;
	
	protected int fontTexture;
	
	public TrueTypeFontRenderer(TrueTypeFont font, char[] usedChars) {
		Validate.notNull(usedChars);
		if (usedChars.length == 0) {
			throw new IllegalArgumentException("usedChars is empty.");
		}
		
		this.font = font;
		this.usedChars = usedChars;
		loadFont(font.ttf);
	}
	
	protected void loadFont(InputStream inputStream) {
		glEnable(GL_TEXTURE_2D);
		
		fontTexture = glGenTextures();
		chardata = STBTTPackedchar.malloc(usedChars.length);
		
		fontInfo = STBTTFontinfo.malloc();
		
		try (STBTTPackContext pc = STBTTPackContext.malloc()) {
			ByteBuffer ttf = ioResourceToByteBuffer(inputStream, 160 * 1024);
			ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_WIDTH * BITMAP_HEIGHT);
			
			if (stbtt_InitFont(fontInfo, ttf) == GL_FALSE) {
				throw new RuntimeException("Couldn't init font");
			}
			
			float scale = stbtt_ScaleForPixelHeight(fontInfo, font.getSize());
			
			font.metrics.init(this, scale);
			
			stbtt_PackBegin(pc, bitmap, BITMAP_WIDTH, BITMAP_HEIGHT, 0, 1, null);
			{
				chardata.position(0);
				stbtt_PackSetOversampling(pc, 1, 1);
				stbtt_PackFontRange(pc, ttf, 0, font.getSize(), 0, chardata);
				chardata.clear();
			}
			stbtt_PackEnd(pc);
			
			glBindTexture(GL_TEXTURE_2D, fontTexture);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_WIDTH, BITMAP_HEIGHT, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		glDisable(GL_TEXTURE_2D);
	}
	
	protected float renderText(String text, float x, float y, Color color) {
		color.bind();
		return getTextWidth(text, x, y, true);
	}
	
	protected float getTextWidth(String text, float x, float y, boolean renderText) {
		xPos.put(0, x);
		yPos.put(0, y);
		chardata.position(0);
		
		textWidth = 0.0f;
		
		if (renderText) {
			glBindTexture(GL_TEXTURE_2D, fontTexture);
			glBegin(GL_QUADS);
		}
		
		for (char ch : text.toString().toCharArray()) {
			if (!canRender(ch)) {
				new RuntimeException("Cannot render char: " + ch + "(" + ((int) ch) + ")").printStackTrace();
				
				if (canRender('?')) {
					ch = '?';
				} else {
					continue;
				}
			}
			
			textX = xPos.get(0);
			
			STBTruetype.stbtt_GetPackedQuad(chardata, BITMAP_WIDTH, BITMAP_HEIGHT, (int) ch, xPos, yPos, quad, true);
			
			textWidth += xPos.get(0) - textX;
			
			if (renderText) {
				drawBoxTex(quad.x0(), quad.y0(), quad.x1(), quad.y1(), quad.s0(), quad.t0(), quad.s1(), quad.t1());
			}
		}
		
		if (renderText) {
			glEnd();
		}
		
		return textWidth;
	}
	
	public boolean canRender(char ch) {
		for (char c : usedChars) {
			if (c == ch) {
				return true;
			}
		}
		
		return false;
	}
	
	protected static void drawBoxTex(float x0, float y0, float x1, float y1, float s0, float t0, float s1, float t1) {
		glTexCoord2f(s0, t0);
		glVertex2f(x0, y0);
		glTexCoord2f(s1, t0);
		glVertex2f(x1, y0);
		glTexCoord2f(s1, t1);
		glVertex2f(x1, y1);
		glTexCoord2f(s0, t1);
		glVertex2f(x0, y1);
	}
	
	protected static ByteBuffer ioResourceToByteBuffer(InputStream inputStream, int bufferSize) throws IOException {
		ByteBuffer buffer;
		
		try (InputStream source = inputStream; ReadableByteChannel rbc = Channels.newChannel(source)) {
			buffer = createByteBuffer(bufferSize);
			
			while (true) {
				int bytes = rbc.read(buffer);
				if (bytes == -1)
					break;
				if (buffer.remaining() == 0) {
					ByteBuffer newBuffer = BufferUtils.createByteBuffer(buffer.capacity() * 2);
					buffer.flip();
					newBuffer.put(buffer);
					buffer = newBuffer;
				}
			}
		}
		
		buffer.flip();
		return buffer;
	}
}
