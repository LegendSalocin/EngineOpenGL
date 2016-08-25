package de.salocin.engine.model;

import static org.lwjgl.opengl.GL11.*;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.util.math.Vector3f;

public class Sphere2D {
	
	private Vector3f middle;
	private float radius;
	private float startAngle = 0.0f;
	private float endAngle = 360.0f;
	private float precision;
	
	public Sphere2D(Vector3f middle, float radius) {
		setMiddle(middle);
		this.radius = radius;
		setPrecision(1.0f);
	}
	
	public void setMiddle(Vector3f middle) {
		this.middle = Validate.notNull(middle);
	}
	
	public Vector3f getMiddle() {
		return middle;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public void setStartAngle(float startAngle) {
		this.startAngle = startAngle;
	}
	
	public float getStartAngle() {
		return startAngle;
	}
	
	public void setEndAngle(float endAngle) {
		this.endAngle = endAngle;
	}
	
	public float getEndAngle() {
		return endAngle;
	}
	
	public void setPrecision(float precision) {
		if (precision <= 0) {
			throw new IllegalArgumentException("precision <= 0");
		}
		
		this.precision = precision;
	}
	
	public float getPrecision() {
		return precision;
	}
	
	public void render() {
		float x;
		float y;
		float angle = 1.0f / (precision * 5.0f);
		
		glBegin(GL_LINE_LOOP);
		
		for (float f = 0.0f; f < Math.PI * 2.0f; f += angle) {
			float deg = (float) Math.toDegrees(f);
			
			if (deg >= startAngle && deg <= endAngle) {
				x = middle.x + (float) Math.sin(f) * radius;
				y = middle.y + (float) Math.cos(f) * radius;
				
				glVertex2f(x, y);
			}
		}
		
		glEnd();
	}
	
}
