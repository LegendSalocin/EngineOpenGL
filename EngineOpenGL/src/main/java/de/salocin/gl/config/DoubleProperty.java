package de.salocin.gl.config;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.impl.config.DoublePropertyImpl;

/**
 * Represents a single property in a configuration file. The value type is a
 * {@link Double}
 */
public interface DoubleProperty extends Property<Double> {
	/**
	 * Alias for {@link #setValue(Double)} with a primitive double variable
	 * 
	 * @param value
	 *            The new property's value
	 */
	default void setDouble(double value) {
		setValue(new Double(value));
	}
	
	/**
	 * Alias for {@link #getValue()} with a primitive double variable
	 * 
	 * @return The property's current value
	 */
	default double getDouble() {
		return getValue().doubleValue();
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
	public static DoubleProperty newInstance(String name, double defaultValue) {
		return newInstance(name, new Double(defaultValue));
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
	public static DoubleProperty newInstance(String name, Double defaultValue) {
		return new DoublePropertyImpl(Validate.notNull(name), Validate.notNull(defaultValue));
	}
}
