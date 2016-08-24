package de.salocin.engine.gui.util;

import de.salocin.engine.utils.config.AbstractProperty;

public class InsetsProperty extends AbstractProperty<Insets> {
	
	public InsetsProperty() {
		this(Insets.NONE);
	}
	
	public InsetsProperty(Insets defaultValue) {
		super(null, defaultValue);
	}
	
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		setValue((Insets) value);
	}
}
