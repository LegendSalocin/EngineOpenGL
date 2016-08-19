package de.salocin.engine.gui.component.pane;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.gui.component.Component;
import de.salocin.engine.gui.layout.LayoutConstraint;
import de.salocin.engine.gui.layout.LayoutManager;
import de.salocin.gl.util.ReflectionUtils;

public abstract class Pane<T extends LayoutConstraint> extends Component {
	
	private Map<T, Component> children = new HashMap<T, Component>();
	private LayoutManager<T> layoutManager;
	
	public Pane(LayoutManager<T> layoutManager) {
		this.layoutManager = Validate.notNull(layoutManager);
	}
	
	public LayoutManager<T> getLayoutManager() {
		return layoutManager;
	}
	
	public Collection<Component> getChildren() {
		return children.values();
	}
	
	protected void add(Component c, T attachment) {
		children.put(attachment, c);
		ReflectionUtils.setFieldValue(c, "parent", this);
	}
	
	protected boolean isConstraintSet(T constraint) {
		for (T c : children.keySet()) {
			if (c.<T> equals(constraint)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void remove(Component child) {
		children.remove(child);
	}
	
	@Override
	public void pack() {
		for (Component component : children.values()) {
			if (component.getPrefSize() == null) {
				component.pack();
			}
		}
		
		layoutManager.layoutComponents(this, children.values());
	}
	
}
