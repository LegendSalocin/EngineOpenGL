package de.salocin.engine.gui.widget;

import de.salocin.engine.display.Renderer;
import de.salocin.engine.display.input.Action;
import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.util.Viewport;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.font.FontMetrics;

public class TextField extends Text {
	
	private Color selectionColor;
	private int cursorPos = 0;
	private int selectionStart = 0;
	private int selectionLength = 0;
	
	public TextField(CharSequence text) {
		super(text);
	}
	
	public int getCursorPos() {
		return cursorPos;
	}
	
	public void setCursorPos(int index) {
		cursorPos = checkIndex(index);
	}
	
	public void insert(int index, String text) {
		checkIndex(index);
		cursorPos = index;
		
		setText(getText().substring(0, index) + text + getText().substring(index, getText().length()));
		
		cursorPos += text.length();
	}
	
	public void delete(int indexFrom, int indexTo) {
		if (indexFrom > indexTo) {
			throw new IllegalArgumentException("indexFrom > indexTo");
		} else if (indexFrom == indexTo) {
			return;
		}
		
		if (cursorPos > indexFrom && cursorPos <= indexTo) {
			cursorPos = indexFrom;
		}
		
		setText(getText().substring(0, checkIndex(indexFrom)) + getText().substring(checkIndex(indexTo), getText().length()));
	}
	
	public void select(int indexFrom, int indexTo) {
		if (indexFrom > indexTo) {
			throw new IllegalArgumentException("indexFrom > indexTo");
		} else if (indexFrom == indexTo) {
			return;
		}
		
		selectionStart = checkIndex(indexFrom);
		selectionLength = checkIndex(indexTo) - selectionStart;
	}
	
	public void setSelectionColor(Color selectionColor) {
		this.selectionColor = selectionColor;
	}
	
	public Color getSelectionColor() {
		return selectionColor;
	}
	
	@Override
	protected void onKey(KeyEvent e) {
		if (e.getAction() != Action.RELEASED) {
			switch (e.getKey()) {
			case KEY_LEFT:
				if (cursorPos > 0) {
					cursorPos--;
				}
				break;
			case KEY_RIGHT:
				if (cursorPos < getText().length()) {
					cursorPos++;
				}
				break;
			case KEY_DELETE:
				int start;
				int end;
				if (selectionLength > 0) {
					start = selectionStart;
					end = selectionStart + selectionLength;
				} else {
					if (cursorPos == getText().length()) {
						break;
					}
					
					delete(cursorPos, cursorPos + 1);
				}
				
				break;
			case KEY_BACKSPACE:
				if (getText().length() > 0) {
					if (selectionLength > 0) {
						start = selectionStart;
						end = selectionStart + selectionLength;
					} else {
						if (cursorPos == 0) {
							break;
						}
						
						delete(cursorPos - 1, cursorPos);
					}
				}
				break;
			default:
				if (e.getKey().isPrintable()) {
					insert(cursorPos, e.getChar());
				}
				break;
			}
		}
	}
	
	@Override
	public void render() {
		super.render();
		
		final FontMetrics m = getTextFont().getMetrics();
		final char[] text = getText().toCharArray();
		final float height = m.getLineHeight();
		final float y = getPosY() + super.textOffsetY - m.getAscent();
		
		float x = getPosX() + super.textOffsetX;
		float selectionX = -1;
		float selectionWidth = -1;
		float cursorX = x;
		
		for (int i = 0; i < text.length; i++) {
			float charWidth = m.getWidth(text[i]);
			
			if (i == selectionStart) {
				selectionX = x;
			}
			
			if (i < cursorPos) {
				cursorX += charWidth;
			}
			
			x += charWidth;
		}
		
		Renderer.renderQuad(cursorX, y, Viewport.scaledWidth(2), height);
	}
	
	protected int checkIndex(int index) {
		if (index < 0 || index > getText().length()) {
			throw new IndexOutOfBoundsException();
		}
		
		return index;
	}
	
}
