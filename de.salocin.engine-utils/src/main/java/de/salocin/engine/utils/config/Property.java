package de.salocin.engine.utils.config;

import de.salocin.engine.event.Callback;
import de.salocin.engine.event.ValueChangeEvent;

/**
 * Represents a single property in a configuration file.
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
	 * Adds a callback to catch events if the {@link #setValue(Object)} method
	 * is called and the value will be changed
	 * 
	 * @param callback
	 *            the callback
	 */
	void addValueChangeCallback(Callback<ValueChangeEvent<T>> callback);
	
}
