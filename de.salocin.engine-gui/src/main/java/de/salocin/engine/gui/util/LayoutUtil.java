package de.salocin.engine.gui.util;

import de.salocin.engine.gui.widget.Widget;
import de.salocin.engine.util.ReflectionUtils;

public class LayoutUtil {
	
	public static void setWidgetPos(Widget widget, float x, float y) {
		ReflectionUtils.invokeMethod(widget, "setPos", x, y);
	}
	
}
