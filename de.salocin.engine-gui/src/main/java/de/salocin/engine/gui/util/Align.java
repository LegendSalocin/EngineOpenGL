package de.salocin.engine.gui.util;

import static de.salocin.engine.gui.util.Align.Horizontal.*;
import static de.salocin.engine.gui.util.Align.Vertical.*;

public class Align implements Cloneable {
	
	public static final Align TOP_LEFT = new Align(LEFT, TOP);
	public static final Align TOP_CENTER = new Align(CENTER, TOP);
	public static final Align TOP_RIGHT = new Align(RIGHT, TOP);
	public static final Align MIDDLE_LEFT = new Align(LEFT, MIDDLE);
	public static final Align MIDDLE_CENTER = new Align(CENTER, MIDDLE);
	public static final Align MIDDLE_RIGHT = new Align(RIGHT, MIDDLE);
	public static final Align BOTTOM_LEFT = new Align(LEFT, BOTTOM);
	public static final Align BOTTOM_CENTER = new Align(CENTER, BOTTOM);
	public static final Align BOTTOM_RIGHT = new Align(RIGHT, BOTTOM);
	public static final Align NONE = MIDDLE_CENTER;
	
	private final Horizontal horizontal;
	private final Vertical vertical;
	private float offsetH;
	private float offsetV;
	
	public Align(Align.Horizontal h, Align.Vertical v) {
		this(h, v, 0.0f, 0.0f);
	}
	
	public Align(Align.Horizontal h, Align.Vertical v, float offsetH, float offsetV) {
		this.horizontal = h;
		this.vertical = v;
		this.offsetH = offsetH;
		this.offsetV = offsetV;
	}
	
	public Horizontal getHorizontal() {
		return horizontal;
	}
	
	public Vertical getVertical() {
		return vertical;
	}
	
	public float getHorizontalOffset() {
		return offsetH;
	}
	
	public float getVerticalOffset() {
		return offsetV;
	}
	
	public Align setHorizonalOffset(float offsetH) {
		Align a = clone();
		a.offsetH = offsetH;
		return a;
	}
	
	public Align setVerticalOffset(float offsetV) {
		Align a = clone();
		a.offsetV = offsetV;
		return a;
	}
	
	@Override
	public Align clone() {
		return new Align(horizontal, vertical, offsetH, offsetV);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Align) {
			Align a = (Align) obj;
			return a.horizontal == horizontal && a.vertical == vertical && a.offsetH == offsetH && a.offsetV == offsetV;
		}
		
		return false;
	}
	
	public static enum Horizontal {
		LEFT,
		CENTER,
		RIGHT;
	}
	
	public static enum Vertical {
		TOP,
		MIDDLE,
		BOTTOM;
	}
	
}
