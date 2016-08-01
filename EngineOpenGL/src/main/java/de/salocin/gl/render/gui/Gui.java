package de.salocin.gl.render.gui;

import static org.lwjgl.opengl.GL11.*;

public class Gui {
	
	public static void enableAlpha() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void disableAlpha() {
		glDisable(GL_BLEND);
	}
	
	public static void enableTexture() {
		glEnable(GL_TEXTURE_2D);
	}
	
	public static void disableTexture() {
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void renderQuad(float x, float y, float width, float height) {
		glBegin(GL_QUADS);
		{
			glVertex2f(x, y);
			glVertex2f(x + width, y);
			glVertex2f(x + width, y + height);
			glVertex2f(x, y + height);
		}
		glEnd();
	}
	
	public static void renderTexture(float x, float y, float width, float height, float textureX, float textureY, float textureWidth, float textureHeight) {
		glBegin(GL_QUADS);
		{
			glTexCoord2d(textureX, textureY);
			glVertex2f(x, y);
			
			glTexCoord2d(textureX + textureWidth, textureY);
			glVertex2f(x + width, y);
			
			glTexCoord2d(textureX + textureWidth, textureY + textureHeight);
			glVertex2f(x + width, y + height);
			
			glTexCoord2d(textureX, textureY + textureHeight);
			glVertex2f(x, y + height);
		}
		glEnd();
	}
	
}
