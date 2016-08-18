package de.salocin.gl.gui;

import de.salocin.gl.util.Renderable;

public interface RenderState extends Renderable {
	
	void init();
	
	void update(long currentTime, long delta);
	
	void render();
	
	void exit();
	
}
