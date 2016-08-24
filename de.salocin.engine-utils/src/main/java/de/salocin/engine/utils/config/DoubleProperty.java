package de.salocin.engine.utils.config;

/**
 * Represents a single property in a configuration file. The value type is a
 * {@link Double}
 */
public class DoubleProperty extends AbstractProperty<Double> {
	
	/**
	 * Creates a new instance of this property with an empty name and none
	 * default value. Not recommended for use with a {@link Configuration},
	 * because values can not be retrieved.
	 * 
	 * @return A new instance of this property
	 */
	public DoubleProperty() {
		this(null, 0.0);
	}
	
	/**
	 * Creates a new instance of this property.
	 * 
	 * @param name
	 *            The property's name. This can not be changed.
	 * @param defaultValue
	 *            The property's default value. This can not be changed.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getName()
	 * @see #getDefaultValue()
	 */
	public DoubleProperty(String name, double defaultValue) {
		super(name, defaultValue);
	}
	
	/**
	 * Creates a new instance of this property.
	 * 
	 * @param name
	 *            The property's name. This can not be changed.
	 * @param defaultValue
	 *            The property's default value. This can not be changed.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getName()
	 * @see #getDefaultValue()
	 */
	public DoubleProperty(String name, Double defaultValue) {
		super(name, defaultValue);
	}
	
	/**
	 * Alias for {@link #setValue(Double)} with a primitive double variable
	 * 
	 * @param value
	 *            The new property's value
	 */
	public void setDouble(double value) {
		setValue(new Double(value));
	}
	
	/**
	 * Alias for {@link #getValue()} with a primitive double variable
	 * 
	 * @return The property's current value
	 */
	public double getDouble() {
		return getValue().doubleValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		
		setDouble(Double.parseDouble(value.toString()));
	}
	
}
