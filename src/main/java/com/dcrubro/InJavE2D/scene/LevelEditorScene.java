package com.dcrubro.InJavE2D.scene;

import com.dcrubro.InJavE2D.input.KeyListener;
import com.dcrubro.InJavE2D.input.MouseListener;
import com.dcrubro.InJavE2D.object.GameObject;
import com.dcrubro.InJavE2D.object.Transform;
import com.dcrubro.InJavE2D.object.components.Sprite;
import com.dcrubro.InJavE2D.object.components.SpriteRenderer;
import com.dcrubro.InJavE2D.object.components.Spritesheet;
import com.dcrubro.InJavE2D.render.Camera;
import com.dcrubro.InJavE2D.scripting.InJavEScript;
import com.dcrubro.InJavE2D.util.AssetPool;
import com.dcrubro.InJavE2D.util.Debug;
import com.dcrubro.InJavE2D.util.EngineLogger;
import imgui.ImGui;
import imgui.ImVec2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class LevelEditorScene extends Scene {

    private GameObject player;
    private GameObject obj1;
    private GameObject obj2;
    private SpriteRenderer obj1sprite;
    private Sprite obj2sprite;

    private transient EngineLogger engineLogger;

    private static final transient Logger LOGGER = LogManager.getLogger(LevelEditorScene.class);

    private Spritesheet sprites;

    public LevelEditorScene() {

    }

    public void init() {
        loadResources();
        load();
        engineLogger = new EngineLogger(getClass().getSimpleName());

        this.sceneName = this.getClass().getSimpleName();
        this.sprites = AssetPool.getSpritesheet("assets/spritesheets/spritesheet.png");
        this.camera = new Camera(new Vector2f(-340, -180));

        if (loadedLevel) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        this.obj1 = new GameObject("Obj1", new Transform(new Vector2f(200,100), new Vector2f(256,256)), -1);

        this.obj1sprite = new SpriteRenderer();
        obj1sprite.setColor(new Vector4f(1,0,0,1));

        this.obj1.addComponent(obj1sprite);
        this.obj1.addComponent(new Rigidbody());


        this.obj2 = new GameObject("Obj2", new Transform(new Vector2f(400,100), new Vector2f(256, 256)), 3);

        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();

        this.obj2sprite = new Sprite();

        this.obj2sprite.setTexture(AssetPool.getTexture("assets/textures/blendImage2.png"));

        obj2SpriteRenderer.setSprite(this.obj2sprite);

        this.obj2.addComponent(obj2SpriteRenderer);

        this.addGameObjectToScene(this.obj1);
        this.addGameObjectToScene(this.obj2);

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
        AssetPool.addSpritesheet("assets/spritesheets/spritesheet.png",
                                 new Spritesheet(AssetPool.getTexture("assets/spritesheets/spritesheet.png"),
                                                 16, 16, 26, 0));
    }

    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;

    @Override
    public void update(float deltaTime) {

        MouseListener.getOrthoX();

        /*camera.position.x -= deltaTime * 50f;
        camera.position.y -= deltaTime * 50f;*/
        //System.out.println(gson.toJson(obj1sprite));

        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            this.obj1.transform.position.x += 60f * deltaTime;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            this.obj1.transform.position.x -= 60f * deltaTime;
        }

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
        ImGui.begin("Sprite Selector");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;

        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)) {
                engineLogger.InfoLog("Button " + i + " clicked!");
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        /*ImGui.begin("Active Object Selector");
        ImVec2 windowPos2 = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize2 = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing2 = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);
        float windowX2_2 = windowPos2.x + windowSize2.x;
        for (GameObject object : this.gameObjects) {
            ImGui.button(object.getName());

            if (ImGui.imageButton()) {
                engineLogger.InfoLog("Button " + i + " clicked!");
            }

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }*/

        ImGui.end();
    }
}
