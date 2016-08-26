package de.salocin.engine.gui.widget;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.display.Renderer;
import de.salocin.engine.display.input.Action;
import de.salocin.engine.display.input.Modifier;
import de.salocin.engine.event.KeyEvent;
import de.salocin.engine.util.Viewport;
import de.salocin.engine.utils.core.Color;
import de.salocin.engine.utils.core.ColoredString;
import de.salocin.engine.utils.font.FontMetrics;

public class TextField extends Text {
	
	private Color selectionColor = Color.BLUE;
	private Color textSelectColor = Color.WHITE;
	private int cursorPos = 0;
	private int selectionStart = 0;
	private int selectionEnd = 0;
	
	public TextField(CharSequence text) {
		super(text);
	}
	
	public int getCursorPos() {
		return cursorPos;
	}
	
	public void setCursorPos(int index) {
		cursorPos = checkIndex(index, "index");
	}
	
	public Color getSelectionColor() {
		return selectionColor;
	}
	
	public void setSelectionColor(Color selectionColor) {
		this.selectionColor = Validate.notNull(selectionColor);
	}
	
	public Color getTextSelectionColor() {
		return textSelectColor;
	}
	
	public void setTextSelectionColor(Color textSelectColor) {
		this.textSelectColor = Validate.notNull(textSelectColor);
	}
	
	public void insert(int index, String text) {
		checkIndex(index, "index");
		cursorPos = index;
		
		setText(getText().substring(0, index) + text + getText().substring(index, getText().length()));
		
		cursorPos += text.length();
	}
	
	public void delete(int indexFrom, int indexTo) {
		checkIndex(indexFrom, "indexFrom");
		checkIndex(indexTo, "indexTo");
		
		if (indexFrom > indexTo) {
			throw new IllegalArgumentException("indexFrom > indexTo");
		} else if (indexFrom == indexTo) {
			return;
		}
		
		if (cursorPos > indexFrom && cursorPos <= indexTo) {
			cursorPos = indexFrom;
		}
		
		resetSelection();
		
		setText(getText().substring(0, indexFrom) + getText().substring(indexTo, getText().length()));
	}
	
	public void select(int indexFrom, int indexTo) {
		int start = checkIndex(indexFrom, "indexFrom");
		int end = checkIndex(indexTo, "indexTo");
		
		if (end < start) {
			int tmp = end;
			end = start;
			start = tmp;
		}
		
		selectionStart = start;
		selectionEnd = end;
	}
	
	public void resetSelection() {
		select(0, 0);
	}
	
	public boolean hasSelection() {
		return selectionStart != selectionEnd;
	}
	
	@Override
	protected void onKey(KeyEvent e) {
		if (e.getAction() != Action.RELEASED) {
			switch (e.getKey()) {
			case KEY_LEFT:
				if (cursorPos > 0) {
					if (Modifier.isControlDown(e.getModifiers())) {
						int start;
						int end;
						
						if (!hasSelection()) {
							start = cursorPos - 1;
							end = cursorPos;
						} else {
							start = selectionStart - 1;
							end = selectionEnd;
						}
						
						select(start, end);
					} else {
						resetSelection();
					}
					
					cursorPos--;
				}
				break;
			case KEY_RIGHT:
				if (cursorPos < getText().length()) {
					if (Modifier.isControlDown(e.getModifiers())) {
						int start;
						int end;
						
						if (!hasSelection()) {
							start = cursorPos + 1;
							end = cursorPos;
						} else {
							start = selectionStart + 1;
							end = selectionEnd;
						}
						
						select(start, end);
					} else {
						resetSelection();
					}
					
					cursorPos++;
				}
				break;
			case KEY_DELETE:
				if (selectionStart != selectionEnd) {
					// TODO
				} else {
					if (cursorPos == getText().length()) {
						break;
					}
					
					delete(cursorPos, cursorPos + 1);
				}
				
				break;
			case KEY_BACKSPACE:
				if (getText().length() > 0) {
					if (hasSelection()) {
						delete(selectionStart, selectionEnd);
						break;
					}
					
					if (cursorPos == 0) {
						break;
					}
					
					delete(cursorPos - 1, cursorPos);
				}
				break;
			default:
				if (e.getKey().isPrintable()) {
					if (selectionStart != selectionEnd) {
						delete(selectionStart, selectionEnd);
					}
					
					insert(cursorPos, e.getChar());
				}
				break;
			}
		}
	}
	
	@Override
	public void render() {
		super.renderBackground();
		
		final FontMetrics m = getTextFont().getMetrics();
		final char[] text = getText().toCharArray();
		final float height = m.getLineHeight();
		final float y = getPosY() + super.textOffsetY - m.getAscent();
		
		float x = getPosX() + super.textOffsetX;
		float cursorX = x;
		
		for (int i = 0; i < text.length; i++) {
			float charWidth = m.getWidth(text[i]);
			
			if (i >= selectionStart && i < selectionEnd) {
				selectionColor.bind();
				Renderer.renderQuad(x, y, charWidth, height);
			}
			
			if (i < cursorPos) {
				cursorX += charWidth;
			}
			
			x += charWidth;
		}
		
		ColoredString cs = new ColoredString();
		cs.add(getText().substring(0, selectionStart), getTextColor());
		cs.add(getText().substring(selectionStart, selectionEnd), textSelectColor);
		cs.add(getText().substring(selectionEnd, getText().length()), getTextColor());
		cs.render(getTextFont(), getPosX() + textOffsetX, getPosY() + textOffsetY);
		
		Color.BLACK.bind();
		Renderer.renderQuad(cursorX, y, Viewport.scaledWidth(2), height);
	}
	
	protected int checkIndex(int index, String msg) {
		if (index < 0 || index > getText().length()) {
			throw new IndexOutOfBoundsException(msg);
		}
		
		return index;
	}
	
}
