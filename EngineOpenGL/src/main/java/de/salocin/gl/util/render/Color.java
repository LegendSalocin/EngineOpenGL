package de.salocin.gl.util.render;

import org.lwjgl.opengl.GL11;

import de.salocin.gl.util.Copyable;

public class Color implements Copyable<Color> {
	
	public static final Color white = Color.fromRGB(0xffffff);
	public static final Color black = Color.fromRGB(0x000000);
	public static final Color red = Color.fromRGB(0xff0000);
	public static final Color blue = Color.fromRGB(0x0000ff);
	public static final Color green = Color.fromRGB(0x00ff00);
	public static final Color yellow = Color.fromRGB(0xffff00);
	
	private float r;
	private float g;
	private float b;
	private float a;
	
	private Color() {
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	private Color(float red, float green, float blue, float alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}
	
	public Color setRed(float red) {
		this.r = checkFloat(red);
		return this;
	}
	
	public float getRed() {
		return r;
	}
	
	public Color setGreen(float green) {
		this.g = checkFloat(green);
		return this;
	}
	
	public float getGreen() {
		return g;
	}
	
	public Color setBlue(float blue) {
		this.b = checkFloat(blue);
		return this;
	}
	
	public float getBlue() {
		return b;
	}
	
	public Color setAlpha(float alpha) {
		this.a = checkFloat(alpha);
		return this;
	}
	
	public float getAlpha() {
		return a;
	}
	
	public int getARGB() {
		int a = (int) (this.a * 255);
		int r = (int) (this.r * 255);
		int g = (int) (this.g * 255);
		int b = (int) (this.b * 255);
		System.out.println("a: " + a);
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
	}
	
	public Color setRGB(int rgb) {
		return setARGB(rgb | 0xFF000000);
	}
	
	public Color setARGB(int argb) {
		a = ((argb >> 24) & 0xFF) / 255.0f;
		r = ((argb >> 16) & 0xFF) / 255.0f;
		g = ((argb >> 8) & 0xFF) / 255.0f;
		b = ((argb >> 0) & 0xFF) / 255.0f;
		return this;
	}
	
	public void bind() {
		GL11.glColor4f(r, g, b, a);
	}
	
	@Override
	public Color copy() {
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
		return new Color().setRGB(rgb);
	}
	
	public static Color fromARGB(int argb) {
		return new Color().setARGB(argb);
	}
	
}
