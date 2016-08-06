import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;

import de.salocin.gl.util.texture.SimpleTexture;
import de.salocin.gl.util.texture.Texture;

public class TrueTypeFont {
	
	private static final int BITMAP_WIDTH = 512;
	private static final int BITMAP_HEIGHT = 512;
	private STBTTPackedchar.Buffer chardata;
	private int texture;
	private Texture _test_texture;
	
	private long _test_window;
	
	public static void main(String[] args) {
		new TrueTypeFont();
	}
	
	public TrueTypeFont() {
		_test_createWindow();
		loadFont(TrueTypeFont.class.getResourceAsStream("FiraSans.ttf"));
		_test_loop();
	}
	
	private void _test_createWindow() {
		glfwInit();
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		_test_window = glfwCreateWindow(800, 600, "TrueTypeFont Test", NULL, NULL);
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		glfwSetWindowPos(_test_window, (vidmode.width() - 800) / 2, (vidmode.height() - 600) / 2);
		
		glfwMakeContextCurrent(_test_window);
		GL.createCapabilities();
		
		glfwShowWindow(_test_window);
	}
	
	private void _test_loop() {
		try {
			_test_texture = new SimpleTexture(TrueTypeFont.class.getResource("Unbenannt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, 800.0, 600.0, 0.0, -1.0, 1.0);
		glMatrixMode(GL_MODELVIEW);
		
		while (!glfwWindowShouldClose(_test_window)) {
			glClear(GL_COLOR_BUFFER_BIT);
			
			glfwPollEvents();
			
			_test_texture.render(0, 0, 800, 600);
			
			glfwSwapBuffers(_test_window);
		}
	}
	
	private void loadFont(InputStream ttf) {
		texture = glGenTextures();
		chardata = STBTTPackedchar.malloc(6 * 128);
		
		try (STBTTPackContext pc = STBTTPackContext.malloc()) {
			ByteBuffer ttfBuffer = IOUtil.ioResourceToByteBuffer("FiraSans.ttf", 160 * 1024);
			ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_WIDTH * BITMAP_HEIGHT);
			
			stbtt_PackBegin(pc, bitmap, BITMAP_WIDTH, BITMAP_HEIGHT, 0, 1, null);
			stbtt_PackSetOversampling(pc, 1, 1);
			stbtt_PackFontRange(pc, ttfBuffer, 0, 50.0f, 32, chardata);
			stbtt_PackEnd(pc);
			
			glBindTexture(GL_TEXTURE_2D, texture);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_WIDTH, BITMAP_HEIGHT, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void drawBoxTC(float x0, float y0, float x1, float y1, float s0, float t0, float s1, float t1) {
		glTexCoord2f(s0, t0);
		glVertex2f(x0, y0);
		glTexCoord2f(s1, t0);
		glVertex2f(x1, y0);
		glTexCoord2f(s1, t1);
		glVertex2f(x1, y1);
		glTexCoord2f(s0, t1);
		glVertex2f(x0, y1);
	}
	
}
