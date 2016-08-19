package de.salocin.engine.util.input;

public enum MouseButton {
	LEFT, MIDDLE, RIGHT, BUTTON_4, BUTTON_5, BUTTON_6, BUTTON_7, BUTTON_8;
	
	public static MouseButton fromOrdinal(int ordinal) {
		for (MouseButton btn : values()) {
			if (btn.ordinal() == ordinal) {
				return btn;
			}
		}
		
		return null;
	}
}
