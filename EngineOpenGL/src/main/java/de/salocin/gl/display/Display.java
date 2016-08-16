package de.salocin.gl.display;

import de.salocin.gl.display.font.Font;
import de.salocin.gl.display.font.FontBuilder;
import de.salocin.gl.gui.RenderState;
import de.salocin.gl.impl.display.DisplayImpl;
import de.salocin.gl.util.engine.Check;

public interface Display {
	
	public static Display getInstance() {
		Check.display();
		return DisplayImpl.instance;
	}
	
	Font getDefaultEngineFont();
	
	FontBuilder getDefaultEngineFontBuilder();
	
	boolean isInitialized();
	
	long getWindowHandle();
	
	void setRenderState(RenderState state);
	
	RenderState getRenderState();
	
	void enableVsync(boolean vsync);
	
	boolean isVsyncEnabled();
	
}
