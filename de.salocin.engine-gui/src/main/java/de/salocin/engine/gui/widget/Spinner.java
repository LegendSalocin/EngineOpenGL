package de.salocin.engine.gui.widget;

import de.salocin.engine.display.input.Action;
import de.salocin.engine.display.input.Key;
import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.gui.widget.spinner.DoubleNumberModel;
import de.salocin.engine.gui.widget.spinner.LongNumberModel;
import de.salocin.engine.gui.widget.spinner.SpinnerModel;

public class Spinner<T> extends TextField {
	
	private final SpinnerModel<T> spinnerModel;
	
	@SuppressWarnings("unchecked")
	public Spinner(long min, long step, long max) {
		this((SpinnerModel<T>) new LongNumberModel(min, step, max));
	}
	
	@SuppressWarnings("unchecked")
	public Spinner(double min, double step, double max) {
		this((SpinnerModel<T>) new DoubleNumberModel(min, step, max));
	}
	
	public Spinner(SpinnerModel<T> spinnerModel) {
		super(null);
		this.spinnerModel = spinnerModel;
		spinnerModel.propertyValue().addValueChangeCallback(e -> {
			String text = e.getNewValue().toString();
			
			if (getText() == null || !getText().equals(text)) {
				setText(text);
			}
		});
		
		spinnerModel.setValue(spinnerModel.getDefault());
	}
	
	public SpinnerModel<T> getSpinnerModel() {
		return spinnerModel;
	}
	
	@Override
	public void setText(CharSequence text) {
		try {
			if (text != null) {
				if (text.toString().trim().isEmpty()) {
					text = spinnerModel.getDefault().toString();
				}
				
				super.setText(text);
				spinnerModel.parse(text.toString());
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	protected boolean isKeyAllowed(Key key) {
		switch (key) {
		case KEY_0:
		case KEY_1:
		case KEY_2:
		case KEY_3:
		case KEY_4:
		case KEY_5:
		case KEY_6:
		case KEY_7:
		case KEY_8:
		case KEY_9:
		case KEY_NUMPAD_0:
		case KEY_NUMPAD_1:
		case KEY_NUMPAD_2:
		case KEY_NUMPAD_3:
		case KEY_NUMPAD_4:
		case KEY_NUMPAD_5:
		case KEY_NUMPAD_6:
		case KEY_NUMPAD_7:
		case KEY_NUMPAD_8:
		case KEY_NUMPAD_9:
			return true;
		default:
			return false;
		}
	}
	
	@Override
	protected void onKey(KeyEvent e, boolean hasFocus) {
		if (!hasFocus || e.getAction() == Action.RELEASED) {
			return;
		}
		
		switch (e.getKey()) {
		case KEY_UP:
			spinnerModel.increase();
			break;
		case KEY_DOWN:
			spinnerModel.decrease();
			break;
		default:
			super.onKey(e, hasFocus);
			break;
		}
	}
	
}
