package de.salocin.gl.util.math;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.util.Copyable;

public class Rectangle implements Copyable<Rectangle> {
	
	private Point point;
	private Dimension dimension;
	
	public Rectangle() {
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public Rectangle(float x, float y, float width, float height) {
		this(new Point(x, y), new Dimension(width, height));
	}
	
	public Rectangle(Point point, Dimension dimension) {
		Validate.notNull(point);
		Validate.notNull(dimension);
		
		this.point = point;
		this.dimension = dimension;
	}
	
	public Rectangle setBounds(float x, float y, float width, float height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		return this;
	}
	
	public Rectangle setPosition(float x, float y) {
		setX(x);
		setY(y);
		return this;
	}
	
	public Rectangle setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
		return this;
	}
	
	public float getX() {
		return point.getX();
	}
	
	public Rectangle setX(float x) {
		point.setX(x);
		onUpdate();
		return this;
	}
	
	public float getY() {
		return point.getY();
	}
	
	public Rectangle setY(float y) {
		point.setY(y);
		onUpdate();
		return this;
	}
	
	public float getWidth() {
		return dimension.getWidth();
	}
	
	public Rectangle setWidth(float width) {
		dimension.setWidth(width);
		onUpdate();
		return this;
	}
	
	public float getHeight() {
		return dimension.getHeight();
	}
	
	public Rectangle setHeight(float height) {
		dimension.setHeight(height);
		onUpdate();
		return this;
	}
	
	public float getStartX() {
		return getX();
	}
	
	public float getStartY() {
		return getY();
	}
	
	public float getEndX() {
		return getX() + getWidth();
	}
	
	public float getEndY() {
		return getY() + getHeight();
	}
	
	public Point getPoint() {
		return point;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public boolean contains(Point point) {
		return point.getX() > this.point.getX() && point.getY() > this.point.getY() && point.getX() < getEndX() && point.getY() < getEndY();
	}
	
	public void onUpdate() {
	}
	
	@Override
	public Rectangle copy() {
		return new Rectangle(point, dimension);
	}
	
}
