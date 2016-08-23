package de.salocin.engine.display.input;

import org.apache.commons.lang3.ArrayUtils;

public enum Modifier {
	
	SHIFT(0x1),
	CONTROL(0x2),
	ALT(0x4),
	SUPER(0x8);
	
	private final int bit;
	
	private Modifier(int bit) {
		this.bit = bit;
	}
	
	public int getBit() {
		return bit;
	}
	
	public static boolean isShiftDown(int mods) {
		return has(mods, SHIFT.bit);
	}
	
	public static boolean isControlDown(int mods) {
		return has(mods, CONTROL.bit);
	}
	
	public static boolean isAltDown(int mods) {
		return has(mods, ALT.bit);
	}
	
	public static boolean isSuperDown(int mods) {
		return has(mods, SUPER.bit);
	}
	
	public static boolean isShiftDown(Modifier[] mods) {
		return has(mods, SHIFT);
	}
	
	public static boolean isControlDown(Modifier[] mods) {
		return has(mods, CONTROL);
	}
	
	public static boolean isAltDown(Modifier[] mods) {
		return has(mods, ALT);
	}
	
	public static boolean isSuperDown(Modifier[] mods) {
		return has(mods, SUPER);
	}
	
	public static Modifier[] getModifiers(int mods) {
		Modifier[] array = new Modifier[0];
		
		for (Modifier m : values()) {
			if (has(mods, m.getBit())) {
				array = ArrayUtils.add(array, m);
			}
		}
		
		return array;
	}
	
	private static boolean has(int mods, int bit) {
		return (mods & bit) == bit;
	}
	
	private static boolean has(Modifier[] mods, Modifier mod) {
		for (Modifier modifier : mods) {
			if (modifier == mod) {
				return true;
			}
		}
		
		return false;
	}
	
}
