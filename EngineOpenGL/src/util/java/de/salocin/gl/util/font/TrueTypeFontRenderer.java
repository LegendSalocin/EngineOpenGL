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
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.system.MemoryUtil;

import de.salocin.gl.util.Color;

/**
 * Not part of the official API
 */
public class TrueTypeFontRenderer {
	
	private static final int BITMAP_WIDTH = 512;
	private static final int BITMAP_HEIGHT = 512;
	
	private final TrueTypeFont font;
	private final float scale;
	protected final char[] usedChars;
	
	private final FloatBuffer xPos = MemoryUtil.memAllocFloat(1);
	private final FloatBuffer yPos = MemoryUtil.memAllocFloat(1);
	
	private STBTTPackedchar.Buffer chardata;
	private STBTTAlignedQuad quad = STBTTAlignedQuad.malloc();
	
	private int fontTexture;
	
	public TrueTypeFontRenderer(TrueTypeFont font, float scale, char[] usedChars) {
		Validate.notNull(usedChars);
		if (usedChars.length == 0) {
			throw new IllegalArgumentException("usedChars is empty.");
		}
		
		this.font = font;
		this.scale = scale;
		this.usedChars = usedChars;
		loadFont(font.ttf);
	}
	
	protected void loadFont(InputStream inputStream) {
		glEnable(GL_TEXTURE_2D);
		
		fontTexture = glGenTextures();
		chardata = STBTTPackedchar.malloc(usedChars.length);
		
		try (STBTTPackContext pc = STBTTPackContext.malloc()) {
			ByteBuffer ttf = ioResourceToByteBuffer(inputStream, 160 * 1024);
			ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_WIDTH * BITMAP_HEIGHT);
			
			stbtt_PackBegin(pc, bitmap, BITMAP_WIDTH, BITMAP_HEIGHT, 0, 1, null);
			{
				chardata.position(0);
				stbtt_PackSetOversampling(pc, 1, 1);
				stbtt_PackFontRange(pc, ttf, 0, scale, 0, chardata);
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
	
	public void renderText(String text, float x, float y) {
		renderText(text, x, y, Color.white);
	}
	
	public void renderText(String text, float x, float y, Color color) {
		xPos.put(0, x);
		yPos.put(0, y);
		
		chardata.position(0);
		
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, fontTexture);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		color.bind();
		
		glBegin(GL_QUADS);
		
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (!canRender(ch)) {
				new RuntimeException("Cannot render char: " + ch + "(" + ((int) ch) + ")").printStackTrace();
				
				if (canRender('?')) {
					ch = '?';
				} else {
					continue;
				}
			}
			
			stbtt_GetPackedQuad(chardata, BITMAP_WIDTH, BITMAP_HEIGHT, ch, xPos, yPos, quad, true);
			drawBoxTex(quad.x0(), quad.y0(), quad.x1(), quad.y1(), quad.s0(), quad.t0(), quad.s1(), quad.t1());
		}
		
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
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
