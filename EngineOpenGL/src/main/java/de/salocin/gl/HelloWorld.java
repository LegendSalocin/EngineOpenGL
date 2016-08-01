package de.salocin.gl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

public class HelloWorld {
	
	public static void main(String[] args) {
		new HelloWorld();
	}
	
	public HelloWorld() {
		if (!glfwInit())
			System.err.println("GLFW init error!");
		
		long window = glfwCreateWindow(800, 600, "Window", 0, 0);
		glfwShowWindow(window);
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			// Clear the screen and depth buffer
			glClear(GL_COLOR_BUFFER_BIT);
			
			// set the color of the quad (R,G,B,A)
			glColor3f(0.5f, 0.5f, 1.0f);
			
			// draw quad
			glBegin(GL_QUADS);
			{
				glVertex2f(0, 0);
				glVertex2f(1, 0);
				glVertex2f(1, -1);
				glVertex2f(0, -1);
			}
			glEnd();
			
			glfwSwapBuffers(window);
		}
		
		glfwTerminate();
	}
	
}