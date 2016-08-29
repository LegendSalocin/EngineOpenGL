package de.salocin.engine.gui.widget;

import de.salocin.engine.display.input.Action;
import de.salocin.engine.event.CallbackHandler;
import de.salocin.engine.event.Event;
import de.salocin.engine.event.MouseButtonEvent;
import de.salocin.engine.event.SimpleCallback;

public class Button extends Text {
	
	private final CallbackHandler<Event> callback = new CallbackHandler<Event>();
	
	public Button(CharSequence title) {
		super(title);
	}
	
	public void click() {
		callback.call(Event.NONE);
	}
	
	public void addActionCallback(SimpleCallback callback) {
		this.callback.add(callback);
	}
	
	@Override
	protected void onMouseButton(MouseButtonEvent e, boolean hasFocus) {
		super.onMouseButton(e, hasFocus);
		
		if (e.getAction() == Action.RELEASED && isMouseOver()) {
			click();
		}
	}
	
}
