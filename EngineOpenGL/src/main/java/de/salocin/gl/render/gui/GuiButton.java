package de.salocin.gl.render.gui;

import de.salocin.gl.event.input.MouseButtonEvent;
import de.salocin.gl.util.input.Action;

public class GuiButton extends GuiTitle {
	
	private ActionListener onAction;
	
	public GuiButton(CharSequence title, float x, float y, float width, float height) {
		super(title, x, y, width, height);
	}
	
	public GuiButton setOnAction(ActionListener run) {
		onAction = run;
		return this;
	}
	
	@Override
	protected void onMousePress(MouseButtonEvent e) {
		super.onMousePress(e);
		if (mouseOver && e.getAction() == Action.RELEASED) {
			click();
		}
	}
	
	public void click() {
		onAction.onButtonPressed();
	}
	
	public static interface ActionListener {
		
		void onButtonPressed();
		
	}
	
}
