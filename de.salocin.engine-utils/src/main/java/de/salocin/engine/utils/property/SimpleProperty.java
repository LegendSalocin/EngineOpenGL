package de.salocin.engine.utils.property;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.event.Callback;
import de.salocin.engine.event.CallbackHandler;
import de.salocin.engine.event.ValueChangeEvent;

/**
 * Represents a single property.<br>
 * <p>
 * This property does not support configurations!<br>
 * You have to override {@link #load(String)} and {@link #save()} by your own.
 * </p>
 */
public class SimpleProperty<T> implements Property<T> {
	
	private final CallbackHandler<ValueChangeEvent<T>> callback = new CallbackHandler<ValueChangeEvent<T>>();
	private final T defaultValue;
	private T value;
	private ReadOnlyProperty<T> readOnly;
	
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
	public SimpleProperty(T defaultValue) {
		this.defaultValue = Validate.notNull(defaultValue);
		setValue(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(T value) {
		ValueChangeEvent<T> e = new ValueChangeEvent<T>(this.value, value == null ? defaultValue : value);
		callback.call(e);
		if (!e.isCanceled()) {
			this.value = e.getNewValue();
			
			readOnly = new ReadOnlyProperty<T>() {
				@Override
				public void addValueChangeCallback(Callback<ValueChangeEvent<T>> callback) {
					SimpleProperty.this.addValueChangeCallback(callback);
				}
				
				@Override
				public T getValue() {
					return this.getValue();
				}
			};
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getValue() {
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addValueChangeCallback(Callback<ValueChangeEvent<T>> callback) {
		this.callback.add(callback);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getValue().toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load(String value) throws Exception {
		throw new UnsupportedOperationException(getClass().getName() + " does not support configuration.");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String save() {
		throw new UnsupportedOperationException(getClass().getName() + " does not support configuration.");
	}
	
	@Override
	public ReadOnlyProperty<T> readOnly() {
		return readOnly;
	}
	
}
