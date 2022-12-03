package com.dcrubro.InJavE2D.scene;

import com.dcrubro.InJavE2D.input.KeyListener;
import com.dcrubro.InJavE2D.object.GameObject;
import com.dcrubro.InJavE2D.object.Transform;
import com.dcrubro.InJavE2D.object.components.Sprite;
import com.dcrubro.InJavE2D.object.components.SpriteRenderer;
import com.dcrubro.InJavE2D.object.components.Spritesheet;
import com.dcrubro.InJavE2D.render.Camera;
import com.dcrubro.InJavE2D.scripting.InJavEScript;
import com.dcrubro.InJavE2D.util.AssetPool;
import com.dcrubro.InJavE2D.util.Debug;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class PoweredInJavEScene extends Scene {

    public GameObject obj1;
    public Sprite obj1sprite;
    public SpriteRenderer obj1spriteRenderer;

    public PoweredInJavEScene() {

    }

    public void init() {
        loadResources();

        this.sceneName = this.getClass().getSimpleName();

        this.camera = new Camera(new Vector2f(-340, -180));

        if (loadedLevel) {
            return;
        }

        this.obj1 = new GameObject("Obj1", new Transform(new Vector2f(0,0), new Vector2f(1920,1080)), -1);

        this.obj1sprite = new Sprite();
        obj1sprite.setTexture(AssetPool.getTexture("engine/textures/powered_injave.png"));
        this.obj1spriteRenderer = new SpriteRenderer();
        this.obj1spriteRenderer.setSprite(obj1sprite);

        this.obj1.addComponent(obj1spriteRenderer);

        this.addGameObjectToScene(this.obj1);

        this.activeGameObject = this.obj1;

        /*String serialized = gson.toJson(obj1);
        Debug.log(serialized);
        GameObject obj = gson.fromJson(serialized, GameObject.class);
        Debug.log(obj.toString());*/

        /*GameObject launchImGui = new GameObject("LaunchImGui", new Transform(new Vector2f(0,-100), new Vector2f(256,128)), 2);
        launchImGui.addComponent(new SpriteRenderer(
                new Sprite(AssetPool.getTexture("assets/textures/launchimgui.png"))
        ));
        this.addGameObjectToScene(launchImGui);*/

        /*int xOffset = 10;
        int yOffset = 10;

        float totalWidth = (float)(600 - xOffset * 2);
        float totalHeight = (float)(300 - yOffset * 2);
        float sizeX = totalWidth / 100.0f;
        float sizeY = totalHeight / 100.0f;

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                float xPos = xOffset + (x * sizeX);
                float yPos = yOffset + (y * sizeY);

                GameObject go = new GameObject("Obj" + x + "" + y, new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
                go.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));
                this.addGameObjectToScene(go);
            }
        }*/

        /*player = new GameObject("Player", new Transform(new Vector2f(-150, -150), new Vector2f(50, 50)));
        player.addComponent(new SpriteRenderer(new Vector4f(50, 50, 1, 1)));
        this.addGameObjectToScene(player);*/


        Debug.log("FixedUpdateDelay: " + String.valueOf(fixedTimeDelay));
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;

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

    @Override
    public void fixedUpdate(float fixedTimeDelay) {

    }

    @Override
    public Scene getCurrentScene() {
        return this;
    }

    @Override
    public void imgui() {

    }
}
