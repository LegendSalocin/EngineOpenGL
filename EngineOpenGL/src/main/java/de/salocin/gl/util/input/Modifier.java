package de.salocin.gl.util.input;

import org.apache.commons.lang3.ArrayUtils;

public enum Modifier {
	
	SHIFT(0x1), CONTROL(0x2), ALT(0x4), SUPER(0x8);
	
	private final int bit;
	
	private Modifier(int bit) {
		this.bit = bit;
	}
	
	public int getBit() {
		return bit;
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
	
}
