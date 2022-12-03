package com.dcrubro.InJavE2D.scripting;

import com.dcrubro.InJavE2D.object.GameObject;
import com.dcrubro.InJavE2D.scene.Scene;

public abstract class InJavEScript {

    protected Scene currentScene;

    public InJavEScript(Scene currentScene, GameObject ghost) {
        this.currentScene = currentScene;
    }

    public void start() { }
    public void awake() { }
    public void update(float deltaTime) { }

    // TODO: Make fixedUpdate work
    public void fixedUpdate() { }
}
