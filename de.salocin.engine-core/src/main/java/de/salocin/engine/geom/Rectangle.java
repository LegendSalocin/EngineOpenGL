package de.salocin.engine.geom;

public class Rectangle {
	
	private Point start = new Point();
	private Point end = new Point();
	
	public Rectangle() {
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public Rectangle(float x, float y, float width, float height) {
		this(new Point(x, y), new Dimension(width, height));
	}
	
	public Rectangle(Point start, Dimension dimension) {
		this(start, new Point(start.toVector().add(dimension.toVector())));
	}
	
	public Rectangle(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	public float getX() {
		return start.getX();
	}
	
	public float getY() {
		return start.getY();
	}
	
	public float getEndX() {
		return end.getX();
	}
	
	public float getEndY() {
		return end.getY();
	}
	
	public float getWidth() {
		return getEndX() - getX();
	}
	
	public float getHeight() {
		return getEndY() - getY();
	}
	
	public Point getStart() {
		return start;
	}
	
	public Point getEnd() {
		return end;
	}
	
	public Dimension getDimension() {
		return new Dimension(end.toVector().subtract(start.toVector()));
	}
	
	public boolean contains(Point point) {
		return point.getX() >= start.getX() && point.getY() >= start.getY() && point.getX() <= end.getX() && point.getY() <= end.getY();
	}
	
}
