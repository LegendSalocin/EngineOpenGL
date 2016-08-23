package de.salocin.engine.gui.util;

import de.salocin.engine.utils.config.AbstractProperty;

public class InsetsProperty extends AbstractProperty<Insets> {
	
	public InsetsProperty(String name) {
		super(name, Insets.NONE);
	}
	
	public InsetsProperty(String name, Insets defaultValue) {
		super(name, defaultValue);
	}
	
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		setValue((Insets) value);
	}
}
