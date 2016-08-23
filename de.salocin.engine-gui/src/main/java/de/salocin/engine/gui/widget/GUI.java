package de.salocin.engine.gui.widget;

import de.salocin.engine.gui.GuiRenderState;

/**
 * Represents a root widget that can be set in {@link GuiRenderState}
 */
public class GUI extends Widget {
	
	public GUI() {
	}
	
	@Override
	public void setMinSize(float width, float height) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setMinWidth(float width) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setMinHeight(float height) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setMaxSize(float width, float height) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setMaxWidth(float width) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setMaxHeight(float height) {
		throw new UnsupportedOperationException();
	}
	
}
