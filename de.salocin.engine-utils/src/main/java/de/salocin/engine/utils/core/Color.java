package de.salocin.engine.utils.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.lwjgl.opengl.GL11;

public class Color implements Cloneable {
	
	public static final Color WHITE = Color.fromRGB(0xffffff);
	public static final Color BLACK = Color.fromRGB(0x000000);
	public static final Color RED = Color.fromRGB(0xff0000);
	public static final Color BLUE = Color.fromRGB(0x0000ff);
	public static final Color GREEN = Color.fromRGB(0x00ff00);
	public static final Color YELLOW = Color.fromRGB(0xffff00);
	
	private float r;
	private float g;
	private float b;
	private float a;
	
	private Color() {
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	private Color(float red, float green, float blue, float alpha) {
		this.r = checkFloat(red);
		this.g = checkFloat(green);
		this.b = checkFloat(blue);
		this.a = checkFloat(alpha);
	}
	
	public float getRed() {
		return r;
	}
	
	public float getGreen() {
		return g;
	}
	
	public float getBlue() {
		return b;
	}
	
	public float getAlpha() {
		return a;
	}
	
	public int getARGB() {
		int a = (int) (this.a * 255);
		int r = (int) (this.r * 255);
		int g = (int) (this.g * 255);
		int b = (int) (this.b * 255);
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
	}
	
	public void bind() {
		GL11.glColor4f(r, g, b, a);
	}
	
	@Override
	public Color clone() {
		return new Color(r, g, b, a);
	}
	
	private float checkFloat(float color) {
		return color < 0.0f ? 0.0f : (color > 1.0f ? 1.0f : color);
	}
	
	public static Color fromFloat(float red, float green, float blue) {
		return fromFloat(red, green, blue, 1.0f);
	}
	
	public static Color fromFloat(float red, float green, float blue, float alpha) {
		return new Color(red, green, blue, alpha);
	}
	
	public static Color fromDouble(double red, double green, double blue) {
		return fromDouble(red, green, blue, 1.0);
	}
	
	public static Color fromDouble(double red, double green, double blue, double alpha) {
		return fromFloat((float) red, (float) green, (float) blue, (float) alpha);
	}
	
	public static Color fromInt(int red, int green, int blue) {
		return fromInt(red, green, blue, 255);
	}
	
	public static Color fromInt(int red, int green, int blue, int alpha) {
		return fromFloat(red / 255.0f, blue / 255.0f, blue / 255.0f, alpha / 255.0f);
	}
	
	public static Color fromRGB(int rgb) {
		return fromARGB(rgb | 0xFF000000);
	}
	
	public static Color fromARGB(int argb) {
		Color c = new Color();
		c.a = ((argb >> 24) & 0xFF) / 255.0f;
		c.r = ((argb >> 16) & 0xFF) / 255.0f;
		c.g = ((argb >> 8) & 0xFF) / 255.0f;
		c.b = ((argb >> 0) & 0xFF) / 255.0f;
		return c;
	}
	
	public static Color fromName(String colorName) {
		try {
			Field field = Color.class.getField(colorName);
			
			if (Modifier.isStatic(field.getModifiers())) {
				return (Color) field.get(null);
			}
		} catch (Exception e) {
			// ignore
		}
		
		return null;
	}
	
}
