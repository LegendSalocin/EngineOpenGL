package de.salocin.engine.utils.property;

import de.salocin.engine.event.Callback;
import de.salocin.engine.event.ValueChangeEvent;

/**
 * Represents a single property.
 * <p>
 * Available properties:
 * </p>
 * <ul>
 * <li>{@link StringProperty}</li>
 * <li>{@link BooleanProperty}</li>
 * <li>{@link LongProperty}</li>
 * <li>{@link DoubleProperty}</li>
 * </ul>
 * 
 * @param <T>
 *            The type of value the property stores.
 */
public interface Property<T> {
	
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
	 * <b>This method is only required for configurations.</b><br>
	 * Parses a string and sets the string as the new value. All subclasses have
	 * to implement this method. They convert the given configuration value to
	 * the property's value type. If that fails, an exception is thrown.
	 * 
	 * @param value
	 *            The property's value
	 * @throws Exception
	 *             if there is a convert error from <code>Object</code> to
	 *             <code>T</code>.
	 * @throws UnsupportedOperationException
	 *             if the property does not support configuration
	 */
	void load(String value) throws Exception;
	
	/**
	 * <b>This method is only required for configurations.</b><br>
	 * Parses the value to a string, so it can easily be stored within a
	 * configuration file.
	 * 
	 * @return The property's value
	 */
	String save();
	
	/**
	 * Adds a callback to catch events if the {@link #setValue(Object)} method
	 * is called and the value will be changed
	 * 
	 * @param callback
	 *            the callback
	 * @throws UnsupportedOperationException
	 *             if the property does not support configuration
	 */
	void addValueChangeCallback(Callback<ValueChangeEvent<T>> callback);
	
}
