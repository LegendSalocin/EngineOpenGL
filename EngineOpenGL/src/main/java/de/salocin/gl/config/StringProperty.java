package de.salocin.gl.config;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.impl.config.StringPropertyImpl;

/**
 * Represents a single property in a configuration file. The value type is a
 * {@link String}
 */
public interface StringProperty extends Property<String> {
	
	/**
	 * Alias for {@link #setValue(String)}
	 * 
	 * @param value
	 *            The new property's value
	 */
	default void setString(String value) {
		setValue(value);
	}
	
	/**
	 * Alias for {@link #getValue()}
	 * 
	 * @return The property's current value
	 */
	default String getString() {
		return getValue();
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
	public static StringProperty newInstance(String name, String defaultValue) {
		return new StringPropertyImpl(Validate.notNull(name), Validate.notNull(defaultValue));
	}
	
}
