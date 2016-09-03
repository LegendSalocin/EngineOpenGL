package de.salocin.engine.utils.property;

import de.salocin.engine.event.Callback;
import de.salocin.engine.event.ValueChangeEvent;

/**
 * Represents a single property, which is only readable.
 * <p>
 * Available properties:
 * </p>
 * <ul>
 * <li>{@link SimpleProperty}</li>
 * <li>{@link StringProperty}</li>
 * <li>{@link BooleanProperty}</li>
 * <li>{@link LongProperty}</li>
 * <li>{@link DoubleProperty}</li>
 * </ul>
 * 
 * @param <T>
 *            The type of value the property stores.
 */
public interface ReadOnlyProperty<T> {
	
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
	
	/**
	 * Returns the current value of the property.
	 * 
	 * @return The property's current value
	 */
	T getValue();
	
}
