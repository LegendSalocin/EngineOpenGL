package de.salocin.gl.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class ByteBufferUtils {
	
	public static ByteBuffer loadImage(InputStream inputStream) throws IOException {
		return loadImage(ImageIO.read(inputStream));
	}
	
	public static ByteBuffer loadImage(BufferedImage image) {
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
	
}
