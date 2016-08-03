package de.salocin.gl.render;

public interface Texture {
	
	int getId();
	
	void bind();
	
	void release();
	
	boolean isReleased();
	
	float getWidth();
	
	float getHeight();
	
	void render(float x, float y);
	
	void render(float x, float y, float width, float height);
	
	void render(float x, float y, float width, float height, float u, float v);
	
	void render(float x, float y, float width, float height, float u, float v, float textureWidth, float textureHeight);
	
}
