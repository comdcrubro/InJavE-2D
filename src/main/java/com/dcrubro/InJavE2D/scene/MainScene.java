package com.dcrubro.InJavE2D.scene;

import UserScripts.ghost.GhostMovement;
import com.dcrubro.InJavE2D.object.GameObject;
import com.dcrubro.InJavE2D.object.Transform;
import com.dcrubro.InJavE2D.object.components.Sprite;
import com.dcrubro.InJavE2D.object.components.SpriteRenderer;
import com.dcrubro.InJavE2D.render.Camera;
import com.dcrubro.InJavE2D.scripting.InJavEScript;
import com.dcrubro.InJavE2D.util.AssetPool;
import org.joml.Vector2f;

public class MainScene extends Scene {

    private GameObject player;
    private GameObject paddle1;
    private Sprite ghostSprite;

    public MainScene() {

    }

    public void init() {
        loadResources();
        sceneName = this.getClass().getSimpleName();

        this.camera = new Camera(new Vector2f(0, 0));

        this.paddle1 = new GameObject("Ghost", new Transform(new Vector2f(-20,550), new Vector2f(128, 128)), 1);

        SpriteRenderer paddle1Renderer = new SpriteRenderer();
        paddle1Renderer.setSprite(this.ghostSprite);

        this.paddle1.addComponent(paddle1Renderer);

        this.addGameObjectToScene(paddle1);

        this.activeGameObject = null;

        this.sceneScripts.add(new GhostMovement(this, paddle1));
    }

    @Override
    public void update(float deltaTime) {
        for (GameObject go : this.gameObjects) {
            go.update(deltaTime);
        }

        for (InJavEScript scr : sceneScripts) {
            scr.update(deltaTime);
        }

        this.renderer.render();
    }

    public Scene getCurrentScene() {
        return this;
    }

    @Override
    public void fixedUpdate(float fixedTimeDelay) { }

    @Override
    public void imgui() {
        super.imgui();
    }

    public void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }
}
