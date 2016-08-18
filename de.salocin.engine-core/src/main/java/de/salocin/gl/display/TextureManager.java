package de.salocin.gl.display;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import de.salocin.gl.impl.display.TextureImpl;
import de.salocin.gl.util.engine.ResourceLocation;
import de.salocin.gl.util.exception.EngineException;

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
		if (textures.containsKey(resourceLocation)) {
			return textures.get(resourceLocation);
		} else {
			try {
				Texture texture = loadTexture(resourceLocation);
				textures.put(resourceLocation, texture);
				return texture;
			} catch (IOException e) {
				new EngineException(e).printStackTrace();
				return null;
			}
		}
	}
	
	public static Texture loadTexture(ResourceLocation resourceLocation) throws IOException {
		return new TextureImpl(resourceLocation);
	}
	
	public static Texture loadTexture(URL url) throws IOException {
		return new TextureImpl(url);
	}
	
	public static Texture loadTexture(InputStream inputStream) throws IOException {
		return new TextureImpl(inputStream);
	}
	
	public static Texture loadTexture(BufferedImage image) {
		return new TextureImpl(image);
	}
	
}
