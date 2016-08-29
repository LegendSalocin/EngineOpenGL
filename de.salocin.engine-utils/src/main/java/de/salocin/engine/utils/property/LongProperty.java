package de.salocin.engine.utils.property;

import org.apache.commons.lang3.Validate;

/**
 * Represents a single property. The value type is a {@link Long}
 */
public class LongProperty extends SimpleProperty<Long> {
	
	private Long min = Long.MIN_VALUE;
	private Long max = Long.MAX_VALUE;
	
	/**
	 * Creates a new instance of this property with no default value.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getDefaultValue()
	 */
	public LongProperty() {
		this(0l);
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
	public LongProperty(Long defaultValue) {
		super(defaultValue);
	}
	
	/**
	 * Sets the minimal value for that property.
	 * 
	 * @param min
	 *            The new min value
	 */
	public LongProperty setMin(Long min) {
		this.min = Validate.notNull(min);
		return this;
	}
	
	/**
	 * Returns the minimal value for that property.
	 * 
	 * @param min
	 *            The min value
	 */
	public Long getMin() {
		return min;
	}
	
	/**
	 * Sets the maximal value for that property.
	 * 
	 * @param min
	 *            The new max value
	 */
	public LongProperty setMax(Long max) {
		this.max = Validate.notNull(max);
		return this;
	}
	
	/**
	 * Returns the maximal value for that property.
	 * 
	 * @param min
	 *            The max value
	 */
	public Long getMax() {
		return max;
	}
	
	/**
	 * Alias for {@link #setValue(Long)} with a primitive long variable
	 * 
	 * @param value
	 *            The new property's value
	 */
	public void setLong(long value) {
		setValue(new Long(value));
	}
	
	/**
	 * Alias for {@link #getValue()} with a primitive long variable
	 * 
	 * @return The property's current value
	 */
	public long getLong() {
		return getValue().longValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load(String value) throws Exception {
		if (value == null) {
			return;
		}
		
		setLong(Long.parseLong(value.toString()));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String save() {
		return String.valueOf(getLong());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Long value) {
		if (value != null) {
			if (value.longValue() < min.longValue()) {
				value = min;
			} else if (value.longValue() > max.longValue()) {
				value = max;
			}
		}
		
		super.setValue(value);
	}
	
}
