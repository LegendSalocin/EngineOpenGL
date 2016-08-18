package de.salocin.gl.config;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.impl.config.ObjectProperty;

/**
 * Represents a single property in a configuration file.
 * 
 * @param <T>
 *            The type of value the property stores.
 */
public interface Property<T> {
	
	/**
	 * Returns the name of the property. This is something like the id of this
	 * property and is unique in a configuration file.
	 * 
	 * @return The property's name
	 */
	String getName();
	
	/**
	 * Returns the default value of the property. If the value in the
	 * configuration file is not present, then this value will be returned by
	 * {@link #getValue()}.
	 * 
	 * @return The property's default value
	 */
	T getDefaultValue();
	
	/**
	 * Changes the value of the property. This can also be null, in that case
	 * the value will automatically be set to the default value
	 * ({@link #getDefaultValue()}).
	 * 
	 * @param value
	 *            The new property's value
	 */
	void setValue(T value);
	
	/**
	 * Returns the current value of the property. This will never be null: If
	 * there is no value set, then {@link #getDefaultValue()} will be returned.
	 * 
	 * @return The property's current value
	 */
	T getValue();
	
	/**
	 * Sets the value to the given object. All subclasses have to implement this
	 * method. They convert the given object to the property's value type. If
	 * that fails, an exception is thrown.
	 * 
	 * @param value
	 *            The new property's value
	 * @throws Exception
	 *             if there is a convert error from <code>Object</code> to
	 *             <code>T</code>.
	 */
	void setObject(Object value) throws Exception;
	
	/**
	 * Alias for {@link #getValue()}
	 * 
	 * @return The property's current value
	 */
	default Object getObject() {
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
	public static Property<Object> newInstance(String name, Object defaultValue) {
		return new ObjectProperty(Validate.notNull(name), Validate.notNull(defaultValue));
	}
	
}
