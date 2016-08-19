package de.salocin.engine.utils.texture;

import org.lwjgl.opengl.GL11;

import de.salocin.engine.util.Viewport;

/**
 * Gives the ability to load and render textures and images in OpenGL. To create
 * an instance, you just have to call one of the load methods in
 * {@link TextureManager}. All <code>float</float> (which are representing the
 * position or the size) are based on the ortho settings in the {@link Viewport}
 * class.
 * 
 * @see TextureManager#loadTexture(java.awt.image.BufferedImage)
 * @see TextureManager#loadTexture(java.io.InputStream)
 * @see TextureManager#loadTexture(java.net.URL)
 * @see TextureManager#loadTexture(de.salocin.gl.util.engine.ResourceLocation)
 */
public interface Texture {
	
	/**
	 * Returns the id of the texture. This is the number which
	 * {@link GL11#glGenTextures()} returns.
	 * 
	 * @return the texture's id
	 */
	int getId();
	
	/**
	 * Binds the texture to the current GL context. <b>Does not enable
	 * <code>TEXTURE_2D</code>!</b><br>
	 * You have to call <code>glEnable(GL_TEXTURE_2D)</code> by your own.
	 */
	void bind();
	
	/**
	 * Releases the texture and deletes it.
	 */
	void release();
	
	/**
	 * Gets the state of the texture.
	 * 
	 * @return <code>true</code> if it is releases, otherwise
	 *         <code>false</code>.
	 * @see #release()
	 */
	boolean isReleased();
	
	/**
	 * Returns the texture's width based on the current {@link Viewport} ortho
	 * settings.
	 * 
	 * @return The width
	 */
	float getWidth();
	
	/**
	 * Returns the texture's height based on the current {@link Viewport} ortho
	 * settings.
	 * 
	 * @return The height
	 */
	float getHeight();
	
	/**
	 * Renders the texture at the given position. The position is based on the
	 * current {@link Viewport} ortho settings.
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 */
	void render(float x, float y);
	
	/**
	 * Renders the texture at the given position. The position is based on the
	 * current {@link Viewport} ortho settings.<br>
	 * The texture will be scaled to match the given width and height.
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 * @param width
	 *            The width the texture will be scaled to.
	 * @param height
	 *            The height the texture will be scaled to.
	 */
	void render(float x, float y, float width, float height);
	
	/**
	 * Renders the texture at the given position. The position is based on the
	 * current {@link Viewport} ortho settings.<br>
	 * The texture will be scaled to match the given width and height.
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 * @param width
	 *            The width the texture will be scaled to.
	 * @param height
	 *            The height the texture will be scaled to.
	 * @param u
	 *            The x position in the texture to draw from.
	 * @param v
	 *            The y position in the texture to draw from.
	 */
	void render(float x, float y, float width, float height, float u, float v);
	
	/**
	 * Renders the texture at the given position. The position is based on the
	 * current {@link Viewport} ortho settings.<br>
	 * The texture will be scaled to match the given width and height.
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 * @param width
	 *            The width the texture will be scaled to.
	 * @param height
	 *            The height the texture will be scaled to.
	 * @param u
	 *            The x position in the texture to draw from.
	 * @param v
	 *            The y position in the texture to draw from.
	 * @param textureWidth
	 *            The width of the part of the texture
	 * @param textureHeight
	 *            The height of the part of the texture
	 */
	void render(float x, float y, float width, float height, float u, float v, float textureWidth, float textureHeight);
	
}
