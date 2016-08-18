package de.salocin.gl.impl.display;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import de.salocin.gl.display.Texture;
import de.salocin.gl.gui.Gui;
import de.salocin.gl.util.engine.ResourceLocation;

public class TextureImpl implements Texture {
	
	protected static int lastBind = -1;
	protected final ByteBuffer texture;
	protected final float textureWidth;
	protected final float textureHeight;
	protected int textureId;
	private boolean released = false;
	
	public TextureImpl(ResourceLocation resourceLocation) throws IOException {
		this(resourceLocation.toURL());
	}
	
	public TextureImpl(URL url) throws IOException {
		this(url.openStream());
	}
	
	public TextureImpl(InputStream inputStream) throws IOException {
		this(ImageIO.read(inputStream));
	}
	
	public TextureImpl(BufferedImage image) {
		this.textureId = glGenTextures();
		this.texture = imageToByteBuffer(image);
		this.textureWidth = image.getWidth();
		this.textureHeight = image.getHeight();
		
		loadTexture(image.getWidth(), image.getHeight());
	}
	
	protected ByteBuffer imageToByteBuffer(BufferedImage image) {
		final int width = image.getWidth();
		final int height = image.getHeight();
		ByteBuffer buffer = BufferUtils.createByteBuffer(4 * width * height);
		
		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < width; x++) {
				int pixel = pixels[y * width + x];
				byte r = (byte) ((pixel >> 16) & 0xFF);
				byte g = (byte) ((pixel >> 8) & 0xFF);
				byte b = (byte) (pixel & 0xFF);
				byte a = (byte) ((pixel >> 24) & 0xFF);
				
				buffer.put(r);
				buffer.put(g);
				buffer.put(b);
				buffer.put(a);
			}
		}
		
		buffer.flip();
		return buffer;
	}
	
	protected void loadTexture(int widthInPixel, int heightInPixel) {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureId);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, widthInPixel, heightInPixel, 0, GL_RGBA, GL_UNSIGNED_BYTE, texture);
		glDisable(GL_TEXTURE_2D);
	}
	
	@Override
	public int getId() {
		return textureId;
	}
	
	@Override
	public void bind() {
		if (isReleased()) {
			throw new RuntimeException("Texture is released.");
		}
		
		if (lastBind == -1 || lastBind != textureId) {
			lastBind = textureId;
			glBindTexture(GL_TEXTURE_2D, textureId);
		}
	}
	
	@Override
	public void release() {
		if (isReleased()) {
			return;
		}
		
		if (lastBind == textureId) {
			glBindTexture(GL_TEXTURE_2D, 0);
			lastBind = -1;
		}
		
		glDeleteTextures(textureId);
		released = true;
		textureId = -1;
	}
	
	@Override
	public boolean isReleased() {
		return released;
	}
	
	@Override
	public float getWidth() {
		return textureWidth;
	}
	
	@Override
	public float getHeight() {
		return textureHeight;
	}
	
	@Override
	public void render(float x, float y) {
		render(x, y, textureWidth, textureHeight);
	}
	
	@Override
	public void render(float x, float y, float width, float height) {
		render(x, y, width, height, 0, 0);
	}
	
	@Override
	public void render(float x, float y, float width, float height, float u, float v) {
		render(x, y, width, height, u, v, textureWidth, textureHeight);
	}
	
	@Override
	public void render(float x, float y, float width, float height, float u, float v, float textureWidth, float textureHeight) {
		glEnable(GL_TEXTURE_2D);
		bind();
		
		Gui.renderTexture(x, y, width, height, u / this.textureWidth, v / this.textureHeight, textureWidth / this.textureWidth, textureHeight / this.textureHeight);
		glDisable(GL_TEXTURE_2D);
	}
	
}
