package de.salocin.engine.gui.widget;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.gui.layout.LayoutConstraint;
import de.salocin.engine.gui.util.Insets;
import de.salocin.engine.gui.util.InsetsProperty;
import de.salocin.engine.gui.util.Themable;
import de.salocin.engine.util.math.Point;
import de.salocin.engine.util.math.Rectangle;
import de.salocin.engine.utils.config.DoubleProperty;
import de.salocin.engine.utils.core.ResourceLocation;
import de.salocin.engine.utils.texture.Texture;
import de.salocin.engine.utils.texture.TextureManager;

public abstract class Widget {
	
	protected Texture background;
	protected Widget parent;
	protected LayoutConstraint layoutConstraint;
	private DoubleProperty posX = new DoubleProperty("x", 0.0);
	private DoubleProperty posY = new DoubleProperty("y", 0.0);
	private DoubleProperty width = new DoubleProperty("width", 0.0);
	private DoubleProperty height = new DoubleProperty("height", 0.0);
	private DoubleProperty minWidth = new DoubleProperty("minWidth", 0.0);
	private DoubleProperty minHeight = new DoubleProperty("minHeight", 0.0);
	private DoubleProperty maxWidth = new DoubleProperty("maxWidth", Float.MAX_VALUE);
	private DoubleProperty maxHeight = new DoubleProperty("maxHeight", Float.MAX_VALUE);
	private InsetsProperty padding = new InsetsProperty("padding");
	private InsetsProperty margin = new InsetsProperty("margin");
	
	public Widget() {
		if (Themable.class.isAssignableFrom(getClass())) {
			ResourceLocation location = ((Themable) this).getBackground();
			if (location != null) {
				background = TextureManager.getTexture(location);
			}
		}
	}
	
	public Widget getParent() {
		return parent;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	public LayoutConstraint getLayoutConstraint() {
		return layoutConstraint;
	}
	
	public float getPosX() {
		return posX.getValue().floatValue();
	}
	
	public float getPosY() {
		return posY.getValue().floatValue();
	}
	
	public float getWidth() {
		return width.getValue().floatValue();
	}
	
	public float getHeight() {
		return height.getValue().floatValue();
	}
	
	public void setMinSize(float width, float height) {
		setMinWidth(width);
		setMinHeight(height);
	}
	
	public void setMinWidth(float width) {
		minWidth.setDouble(width);
	}
	
	public void setMinHeight(float height) {
		minHeight.setDouble(height);
	}
	
	public float getMinWidth() {
		return minWidth.getValue().floatValue();
	}
	
	public float getMinHeight() {
		return minHeight.getValue().floatValue();
	}
	
	public void setMaxSize(float width, float height) {
		setMinWidth(width);
		setMinHeight(height);
	}
	
	public void setMaxWidth(float width) {
		maxWidth.setDouble(width);
	}
	
	public void setMaxHeight(float height) {
		maxHeight.setDouble(height);
	}
	
	public float getMaxWidth() {
		return maxWidth.getValue().floatValue();
	}
	
	public float getMaxHeight() {
		return maxHeight.getValue().floatValue();
	}
	
	public boolean isInside(float x, float y) {
		return new Rectangle(getPosX(), getPosY(), getWidth(), getHeight()).contains(new Point(x, y));
	}
	
	public void setPadding(Insets padding) {
		this.padding.setValue(Validate.notNull(padding, "padding"));
	}
	
	public Insets getPadding() {
		return padding.getValue();
	}
	
	public void setMargin(Insets margin) {
		this.margin.setValue(Validate.notNull(margin, "margin"));
	}
	
	public Insets getMargin() {
		return margin.getValue();
	}
	
	protected void setPackSize(float prefWidth, float prefHeight) {
		if (prefWidth < getMinWidth()) {
			prefWidth = getMinWidth();
		} else if (prefWidth > getMaxWidth()) {
			prefWidth = getMaxWidth();
		}
		
		if (prefHeight < getMinHeight()) {
			prefHeight = getMinHeight();
		} else if (prefHeight > getMaxHeight()) {
			prefHeight = getMaxHeight();
		}
		
		width.setDouble(prefWidth);
		height.setDouble(prefHeight);
	}
	
	protected void setPos(float x, float y) {
		posX.setDouble(x);
		posY.setDouble(y);
	}
	
	protected void pack() {
	}
	
	public void render() {
		if (background != null) {
			background.render(getPosX(), getPosY(), getWidth(), getHeight());
		}
	}
	
}
