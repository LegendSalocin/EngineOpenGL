package de.salocin.engine.utils.property;

import org.apache.commons.lang3.Validate;

/**
 * Represents a single property. The value type is a {@link Double}
 */
public class DoubleProperty extends SimpleProperty<Double> {
	
	private Double min = Double.MIN_VALUE;
	private Double max = Double.MAX_VALUE;
	
	/**
	 * Creates a new instance of this property with no default value.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getDefaultValue()
	 */
	public DoubleProperty() {
		this(0.0);
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
	public DoubleProperty(Double defaultValue) {
		super(defaultValue);
	}
	
	/**
	 * Sets the minimal value for that property.
	 * 
	 * @param min
	 *            The new min value
	 */
	public DoubleProperty setMin(Double min) {
		this.min = Validate.notNull(min);
		return this;
	}
	
	/**
	 * Returns the minimal value for that property.
	 * 
	 * @param min
	 *            The min value
	 */
	public Double getMin() {
		return min;
	}
	
	/**
	 * Sets the maximal value for that property.
	 * 
	 * @param min
	 *            The new max value
	 */
	public DoubleProperty setMax(Double max) {
		this.max = Validate.notNull(max);
		return this;
	}
	
	/**
	 * Returns the maximal value for that property.
	 * 
	 * @param min
	 *            The max value
	 */
	public Double getMax() {
		return max;
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
	public void load(String value) throws Exception {
		if (value == null) {
			return;
		}
		
		setDouble(Double.parseDouble(value.toString()));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String save() {
		return String.valueOf(getDouble());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Double value) {
		if (value != null) {
			if (value.doubleValue() < min.doubleValue()) {
				value = min;
			} else if (value.doubleValue() > max.doubleValue()) {
				value = max;
			}
		}
		
		super.setValue(value);
	}
	
}
