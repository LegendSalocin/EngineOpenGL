package de.salocin.engine.utils.config;

import de.salocin.engine.event.Callback;
import de.salocin.engine.event.CallbackHandler;
import de.salocin.engine.event.ValueChangeEvent;

public abstract class AbstractProperty<T> implements Property<T> {
	
	private final CallbackHandler<ValueChangeEvent<T>> callback = new CallbackHandler<ValueChangeEvent<T>>();
	private final String name;
	private final T defaultValue;
	private T value;
	
	protected AbstractProperty(String name, T defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
		setValue(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
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
	public abstract void setObject(Object value) throws Exception;
	
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
	public boolean equals(Object obj) {
		if (obj instanceof Property) {
			return ((Property<?>) obj).getName().equalsIgnoreCase(getName());
		}
		
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getObject().toString();
	}
	
}
