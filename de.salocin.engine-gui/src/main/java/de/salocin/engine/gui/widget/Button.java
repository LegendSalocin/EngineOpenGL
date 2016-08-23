package de.salocin.engine.gui.widget;

import de.salocin.engine.gui.GuiPlugin;
import de.salocin.engine.gui.util.Themable;
import de.salocin.engine.utils.core.ResourceLocation;

public class Button extends Text implements Themable {
	
	public Button(CharSequence title) {
		super(title);
	}
	
	@Override
	public ResourceLocation getBackground() {
		return ResourceLocation.newInstance(GuiPlugin.instance, "/theme/button.png");
	}
	
}
