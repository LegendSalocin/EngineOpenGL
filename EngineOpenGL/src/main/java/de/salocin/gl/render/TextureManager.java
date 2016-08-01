package de.salocin.gl.render;

import java.io.IOException;
import java.util.HashMap;

import de.salocin.gl.util.EngineException;
import de.salocin.gl.util.ResourceLocation;

public class TextureManager {
	
	private static final HashMap<ResourceLocation, Texture> textures = new HashMap<ResourceLocation, Texture>();
	
	public static Texture getTexture(ResourceLocation resourceLocation) {
		if (textures.containsKey(resourceLocation)) {
			return textures.get(resourceLocation);
		} else {
			try {
				Texture texture = new SimpleTexture(resourceLocation);
				textures.put(resourceLocation, texture);
				return texture;
			} catch (IOException e) {
				new EngineException(e).printStackTrace();
				return null;
			}
			
		}
	}
	
}
