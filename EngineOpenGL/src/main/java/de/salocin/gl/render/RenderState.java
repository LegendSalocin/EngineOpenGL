package de.salocin.gl.render;

public interface RenderState {
	
	void init();
	
	void update(long currentTime, long delta);
	
	void render();
	
	void exit();
	
}
