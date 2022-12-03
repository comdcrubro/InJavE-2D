package com.dcrubro.InJavE2D.scene;

import com.dcrubro.InJavE2D.gson.ComponentDeserializer;
import com.dcrubro.InJavE2D.gson.GameObjectDeserializer;
import com.dcrubro.InJavE2D.object.Component;
import com.dcrubro.InJavE2D.object.GameObject;
import com.dcrubro.InJavE2D.render.Camera;
import com.dcrubro.InJavE2D.render.Renderer;
import com.dcrubro.InJavE2D.scripting.InJavEScript;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected String sceneName;
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    protected float fixedTimeDelay;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected List<InJavEScript> sceneScripts = new ArrayList<>();
    protected GameObject activeGameObject = null;
    protected boolean loadedLevel = false;
    protected transient Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Component.class, new ComponentDeserializer())
            .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
            .create();

    public Scene() { }

    public void init() { }

    public void start() {
        for (GameObject gameobject : gameObjects) {
            gameobject.start();
            this.renderer.add(gameobject);
        }

        for (InJavEScript script : sceneScripts) {
            script.start();
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject) {
        if (!isRunning) {
            gameObjects.add(gameObject);
        } else {
            gameObjects.add(gameObject);
            gameObject.start();
            this.renderer.add(gameObject);
        }
    }

    public void addScriptToScene(InJavEScript script) {
        if (!isRunning) {
            sceneScripts.add(script);
        } else {
            sceneScripts.add(script);
            script.awake();
        }
    }

    public abstract void update(float deltaTime);

    public abstract Scene getCurrentScene();

    // TODO: Fix the 'fixedUpdate()' function to work.
    public abstract void fixedUpdate(float fixedTimeDelay);

    public Camera getCamera() {
        return this.camera;
    }

    public void sceneImGui() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }

        imgui();
    }

    public void imgui() { }

    public void saveExit() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        try {
            FileWriter writer = new FileWriter("engine/saves/"+sceneName+".txt");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        String inFile = "";

        try {
            inFile = new String(Files.readAllBytes(Paths.get("engine/saves/"+sceneName+".txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inFile.equals("")) {
            GameObject[] objects = gson.fromJson(inFile, GameObject[].class);
            for (int i = 0; i < objects.length; i++) {
                addGameObjectToScene(objects[i]);
            }
            this.loadedLevel = true;
        }
    }

    // Fixed time delay getter/setter
    public float getFixedTimeDelay() { return this.fixedTimeDelay; }
    public void setFixedTimeDelay(float fixedTimeDelay) { this.fixedTimeDelay = fixedTimeDelay; }
}
