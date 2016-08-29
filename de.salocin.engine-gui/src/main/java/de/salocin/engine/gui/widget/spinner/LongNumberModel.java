package de.salocin.engine.gui.widget.spinner;

import de.salocin.engine.utils.property.LongProperty;
import de.salocin.engine.utils.property.Property;

public class LongNumberModel implements SpinnerNumberModel<Long> {
	
	private final LongProperty property = new LongProperty();
	private final Long min;
	private final Long step;
	private final Long max;
	
	public LongNumberModel() {
		this(Long.MIN_VALUE, 1l, Long.MAX_VALUE);
	}
	
	public LongNumberModel(Long min, Long step, Long max) {
		this.min = min;
		this.step = step;
		this.max = max;
	}
	
	@Override
	public Long getMin() {
		return min;
	}
	
	@Override
	public Long getStep() {
		return step;
	}
	
	@Override
	public Long getMax() {
		return max;
	}
	
	@Override
	public void setValue(Long value) {
		if (value < min) {
			value = min;
		} else if (value > max) {
			value = max;
		}
		
		property.setValue(value);
	}
	
	@Override
	public Long getValue() {
		return property.getValue();
	}
	
	@Override
	public Property<Long> propertyValue() {
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
		setValue(Long.parseLong(text));
	}
	
}