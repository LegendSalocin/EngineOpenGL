package de.salocin.engine.gui.widget;

import de.salocin.engine.display.Renderer;
import de.salocin.engine.display.input.Action;
import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.util.Viewport;
import de.salocin.engine.utils.font.FontMetrics;

public class TextField extends Text {
	
	private int cursorPos = 0;
	private int selectionStart = 0;
	private int selectionLength = 0;
	
	public TextField(CharSequence text) {
		super(null);
		
		propertyText().addValueChangeCallback(e -> {
			if (e.getOldValue() == null) {
				cursorPos = e.getNewValue().length();
			}
		});
		
		setText(text);
	}
	
	public int getCursorPos() {
		return cursorPos;
	}
	
	public void setCursorPos(int index) {
		cursorPos = checkIndex(index);
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
					setText(getText() + e.getChar());
					cursorPos++;
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
		
		float x = getPosX() + super.textOffsetX;
		float y = getPosY() + super.textOffsetY - m.getAscent();
		float width = Viewport.scaledWidth(2);
		float height = m.getLineHeight();
		
		for (int i = 0; i < text.length; i++) {
			if (i < cursorPos) {
				x += m.getWidth(text[i]);
			}
		}
		
		Renderer.renderQuad(x, y, width, height);
	}
	
	protected int checkIndex(int index) {
		if (index < 0 || index > getText().length()) {
			throw new IndexOutOfBoundsException();
		}
		
		return index;
	}
	
}
