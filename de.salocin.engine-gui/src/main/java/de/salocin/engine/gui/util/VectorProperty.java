package de.salocin.engine.gui.util;

import de.salocin.engine.geom.Vector3f;
import de.salocin.engine.utils.config.AbstractProperty;

public class VectorProperty extends AbstractProperty<Vector3f> {
	
	public VectorProperty() {
		this(new Vector3f());
	}
	
	public VectorProperty(Vector3f defaultValue) {
		super(null, defaultValue);
	}
	
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		
		setValue((Vector3f) value);
	}
	
}
