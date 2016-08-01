package de.salocin.gl.util;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class ByteBufferLoader {
	
	private final BufferedImage image;
	
	public ByteBufferLoader(BufferedImage img) {
		image = img;
	}
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public ByteBuffer load() {
		ByteBuffer buffer = BufferUtils.createByteBuffer(4 * image.getWidth() * image.getHeight());
		
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
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
	
}
