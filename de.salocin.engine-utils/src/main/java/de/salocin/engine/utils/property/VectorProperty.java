package de.salocin.engine.utils.property;

import de.salocin.engine.geom.Vector2f;
import de.salocin.engine.geom.Vector3f;

public class VectorProperty extends SimpleProperty<Vector3f> {
	
	/**
	 * Creates a new instance of this property with no default value.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getDefaultValue()
	 */
	public VectorProperty() {
		this(new Vector3f());
	}
	
	/**
	 * Creates a new instance of this property.
	 * 
	 * @param defaultValue
	 *            The property's default value. This can not be changed.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getDefaultValue()
	 */
	public VectorProperty(Vector2f defaultValue) {
		super(defaultValue.toVector3f());
	}
	
	/**
	 * Creates a new instance of this property.
	 * 
	 * @param defaultValue
	 *            The property's default value. This can not be changed.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getDefaultValue()
	 */
	public VectorProperty(Vector3f defaultValue) {
		super(defaultValue);
	}
	
	/**
	 * Alias for {@link #setValue(Vector3f))}
	 * 
	 * @param value
	 *            The new property's value
	 */
	public void setVector2f(Vector2f value) {
		setValue(value.toVector3f());
	}
	
	/**
	 * Alias for {@link #setValue(Vector3f))}
	 * 
	 * @param value
	 *            The new property's value
	 */
	public void setVector3f(Vector3f value) {
		setValue(value);
	}
	
	/**
	 * Alias for {@link #getValue()}
	 * 
	 * @return The property's current value
	 */
	public Vector2f getVector2f() {
		return getValue().toVector2f();
	}
	
	/**
	 * Alias for {@link #getValue()}
	 * 
	 * @return The property's current value
	 */
	public Vector3f getVector3f() {
		return getValue();
	}
	
	@Override
	public void load(String value) throws Exception {
		if (value == null) {
			return;
		}
		
		String[] split = value.split(";");
		float x = Float.parseFloat(split[0]);
		float y = Float.parseFloat(split[1]);
		float z = Float.parseFloat(split[2]);
		setValue(new Vector3f(x, y, z));
	}
	
	@Override
	public String save() {
		return getValue().x + ";" + getValue().y + ";" + getValue().z;
	}
	
}
