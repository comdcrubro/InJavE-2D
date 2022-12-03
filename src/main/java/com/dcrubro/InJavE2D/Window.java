package com.dcrubro.InJavE2D;

import com.dcrubro.InJavE2D.imgui.ImGuiLayer;
import com.dcrubro.InJavE2D.input.KeyListener;
import com.dcrubro.InJavE2D.input.MouseListener;
import com.dcrubro.InJavE2D.scene.*;
import com.dcrubro.InJavE2D.util.Debug;
import com.dcrubro.InJavE2D.util.Time;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;
    private ImGuiLayer imGuiLayer;

    private boolean isFirstRun = true;
    private boolean isDebug;

    public float r, g, b, a;
    public float fixedTimeDelay;

    private static Window window = null;

    private static Scene currentScene;

    private Window() {
        this.width = 1024;
        this.height = 720;
        this.title = "InJavE2D Window";
        r = 1;
        b = 1;
        g = 1;
        a = 1;
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                Debug.log("LOG: (Window) Loaded new scene: " + currentScene.getClass().getSimpleName());
                break;
            case 1:
                currentScene = new LevelScene();
                Debug.log("LOG: (Window) Loaded new scene: " + currentScene.getClass().getSimpleName());
                break;
            case 2:
                currentScene = new MainScene();
                Debug.log("LOG: (Window) Loaded new scene: " + currentScene.getClass().getSimpleName());
                break;
            case 3:
                currentScene = new PoweredInJavEScene();
                Debug.log("LOG: (Window) Loaded new scene: " + currentScene.getClass().getSimpleName());
                break;
            default:
                assert false: "Unknown Scene: '" + newScene + "'. Are you missing an extends Scene?";
                break;
        }

        currentScene.load();
        currentScene.init();
        currentScene.start();
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Scene getScene() { return get().currentScene; }

    public void run(boolean enableDebug, float fixedTimeDelay) {
        /*this.width = width;
        this.height = height;*/
        isDebug = enableDebug;
        this.fixedTimeDelay = fixedTimeDelay;
        Debug.print("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();

        //free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        //Error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Init GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to init GLFW!");
        }

        //configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //create window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create GLFW window!");
        }

        //Mouse callbacks
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        //Keyboard callbacks
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        //ImGui callbacks
        glfwSetWindowSizeCallback(glfwWindow, (window, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        //make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        //enable v-sync ---(work on this function later)---
        glfwSwapInterval(1);

        //make window visible
        glfwShowWindow(glfwWindow);

        /*
        This line is critical for LWJGL's interoperation with GLFW's
        OpenGL context, or any context that is managed externally.
        LWJGL detects the context that is current in the current thread,
        creates the GLCapabilities instance and makes the OpenGL
        bindings available for use.
        */
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        this.imGuiLayer = new ImGuiLayer(glfwWindow);
        this.imGuiLayer.initImGui();

        Window.changeScene(0);
        currentScene.setFixedTimeDelay(fixedTimeDelay);
        LogManager.getLogger().info("Initialized new Window!");
    }

    public void loop() {

        float beginTime = Time.getTime();
        float endTime;
        float deltaTime = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {

            //poll events
            glfwPollEvents();

            //set bg color to blue
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (deltaTime >= 0)
                currentScene.update(deltaTime);

            this.imGuiLayer.update(deltaTime, currentScene);
            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;



            //System.out.println("Render complete!");
        }
        currentScene.saveExit();
    }

    public static int getWidth() {
        return get().width;
    }

    public static void setWidth(int width) {
        get().width = width;
    }

    public static int getHeight() {
        return get().height;
    }

    public static void setHeight(int height) {
        get().height = height;
    }

    public static void setWindowSize(int width, int height) {
        glfwSetWindowSize(get().glfwWindow, width, height);
    }
}