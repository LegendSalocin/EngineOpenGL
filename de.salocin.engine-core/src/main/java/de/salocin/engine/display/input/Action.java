package de.salocin.engine.display.input;

public enum Action {
	
	PRESSED, RELEASED, REPEATED;
	
	public static Action fromOrdinal(int ordinal) {
		for (Action btn : values()) {
			if (btn.ordinal() == ordinal) {
				return btn;
			}
		}
		
		return null;
	}
	
}
