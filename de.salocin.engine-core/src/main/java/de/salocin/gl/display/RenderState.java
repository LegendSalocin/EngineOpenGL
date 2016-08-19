package de.salocin.gl.display;

public interface RenderState {
	
	void init();
	
	void update(long currentTime, long delta);
	
	void render();
	
	void exit();
	
}
