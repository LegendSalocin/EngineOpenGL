package de.salocin.engine.utils.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.utils.core.ResourceLocation;

/**
 * Contains static methods to load textures.
 */
public class TextureManager {
	
	private static final HashMap<ResourceLocation, Texture> textures = new HashMap<ResourceLocation, Texture>();
	
	private TextureManager() {
	}
	
	/**
	 * Converts the given {@link ResourceLocation} to a {@link Texture}. If the
	 * Texture is not loaded, this method will automatically load the texture.
	 * You don't have to call {@link #loadTexture(ResourceLocation)} by
	 * yourself.
	 * 
	 * @param resourceLocation
	 *            The resource location where the texture is located
	 * @return The loaded texture
	 */
	public static Texture getTexture(ResourceLocation resourceLocation) {
		Validate.notNull(resourceLocation);
		
		if (textures.containsKey(resourceLocation)) {
			return textures.get(resourceLocation);
		} else {
			try {
				Texture texture = loadTexture(resourceLocation);
				textures.put(resourceLocation, texture);
				return texture;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static Texture loadTexture(ResourceLocation resourceLocation) throws IOException {
		return new SimpleTexture(resourceLocation);
	}
	
	public static Texture loadTexture(URL url) throws IOException {
		return new SimpleTexture(url);
	}
	
	public static Texture loadTexture(InputStream inputStream) throws IOException {
		return new SimpleTexture(inputStream);
	}
	
	public static Texture loadTexture(BufferedImage image) {
		return new SimpleTexture(image);
	}
	
}
