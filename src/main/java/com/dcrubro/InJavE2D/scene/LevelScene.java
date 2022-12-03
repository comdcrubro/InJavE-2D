package com.dcrubro.InJavE2D.scene;

public class LevelScene extends Scene {

    public LevelScene() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void init() {
        sceneName = this.getClass().getSimpleName();
    }

    @Override
    public Scene getCurrentScene() {
        return this;
    }

    @Override
    public void fixedUpdate(float fixedTimeDelay) {

    }
}
