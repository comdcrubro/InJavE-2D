package com.dcrubro.InJavE2D.util;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {
    public static float getTime() { return (float)glfwGetTime(); }
}
