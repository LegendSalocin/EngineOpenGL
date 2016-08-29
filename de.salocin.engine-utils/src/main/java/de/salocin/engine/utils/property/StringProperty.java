package de.salocin.engine.utils.property;

/**
 * Represents a single property. The value type is a {@link String}
 */
public class StringProperty extends SimpleProperty<String> {
	
	/**
	 * Creates a new instance of this property with no default value.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getDefaultValue()
	 */
	public StringProperty() {
		this("");
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
	public StringProperty(String defaultValue) {
		super(defaultValue);
	}
	
	/**
	 * Alias for {@link #setValue(String)}
	 * 
	 * @param value
	 *            The new property's value
	 */
	public void setString(String value) {
		setValue(value);
	}
	
	/**
	 * Alias for {@link #getValue()}
	 * 
	 * @return The property's current value
	 */
	public String getString() {
		return getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load(String value) throws Exception {
		if (value == null) {
			return;
		}
		
		setString(value.toString());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String save() {
		return getString();
	}
	
}
