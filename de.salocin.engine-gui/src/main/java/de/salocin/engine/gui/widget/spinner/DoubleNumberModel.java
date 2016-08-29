package de.salocin.engine.gui.widget.spinner;

import de.salocin.engine.utils.config.DoubleProperty;
import de.salocin.engine.utils.config.Property;

public class DoubleNumberModel implements SpinnerNumberModel<Double> {
	
	private final DoubleProperty property = new DoubleProperty();
	private final Double min;
	private final Double step;
	private final Double max;
	
	public DoubleNumberModel() {
		this(Double.MIN_VALUE, 1.0, Double.MAX_VALUE);
	}
	
	public DoubleNumberModel(Double min, Double step, Double max) {
		this.min = min;
		this.step = step;
		this.max = max;
	}
	
	@Override
	public Double getMin() {
		return min;
	}
	
	@Override
	public Double getStep() {
		return step;
	}
	
	@Override
	public Double getMax() {
		return max;
	}
	
	@Override
	public void setValue(Double value) {
		if (value < min) {
			value = min;
		} else if (value > max) {
			value = max;
		}
		
		property.setValue(value);
	}
	
	@Override
	public Double getValue() {
		return property.getValue();
	}
	
	@Override
	public Property<Double> propertyValue() {
		return property;
	}
	
	@Override
	public void increase() {
		setValue(getValue() + getStep());
	}
	
	@Override
	public void decrease() {
		setValue(getValue() - getStep());
	}
	
	@Override
	public void parse(String text) throws Exception {
		setValue(Double.parseDouble(text));
	}
	
}