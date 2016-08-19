package de.salocin.engine.event.display;

public class DisplaySizeChangeEvent extends DisplayEvent {
	
	private final int oldWidth;
	private final int oldHeight;
	private final int newWidth;
	private final int newHeight;
	
	public DisplaySizeChangeEvent(int oldWidth, int oldHeight, int newWidth, int newHeight) {
		this.oldWidth = oldWidth;
		this.oldHeight = oldHeight;
		this.newWidth = newWidth;
		this.newHeight = newHeight;
	}
	
	public int getOldWidth() {
		return oldWidth;
	}
	
	public int getOldHeight() {
		return oldHeight;
	}
	
	public float getNewWidth() {
		return newWidth;
	}
	
	public float getNewHeight() {
		return newHeight;
	}
	
	@Override
	public boolean isCancelable() {
		return false;
	}
	
}
