package de.salocin.engine.utils.core;

import java.util.ArrayList;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.utils.font.Font;

public class ColoredString implements CharSequence {
	
	private StringBuilder sb = new StringBuilder();
	private ArrayList<Color> colors = new ArrayList<Color>();
	
	public void add(CharSequence string, Color color) {
		Validate.notNull(string, "string");
		Validate.notNull(color, "color");
		
		sb.append(string);
		for (int i = 0; i < string.length(); i++) {
			colors.add(color);
		}
	}
	
	public void add(char c, Color color) {
		add(String.valueOf(c), color);
	}
	
	@Override
	public int length() {
		return sb.length();
	}
	
	@Override
	public char charAt(int index) {
		return sb.charAt(index);
	}
	
	@Override
	public CharSequence subSequence(int start, int end) {
		return sb.subSequence(start, end);
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	public void render(Font font, float x, float y) {
		font.getRenderer().renderText(toString(), x, y, colors.toArray(new Color[colors.size()]));
	}
	
}
