package com.dcrubro.InJavE2D.object.components;

import com.dcrubro.InJavE2D.object.Component;
import com.dcrubro.InJavE2D.object.Transform;
import com.dcrubro.InJavE2D.render.Texture;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color = new Vector4f(1,1,1,1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = true;

    /*public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
        this.isDirty = true;
    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1,1,1,1);
        this.isDirty = true;
    }*/

    @Override
    public void start() {
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float deltaTime) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void imgui() {
        float[] imColors = {color.x, color.y, color.z, color.w};
        if (ImGui.colorPicker4("Color Picker: ", imColors)) {
            this.color.set(imColors[0], imColors[1], imColors[2], imColors[3]);
            this.isDirty = true;
        }
    }

    public Vector4f getColor() { return this.color; }
    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.color = color;
            this.isDirty = true;
        }
    }

    public Texture getTexture() { return sprite.getTexture(); }
    public Vector2f[] getTexCoords() { return sprite.getTexCoords(); }

    public void setSprite(Sprite sprite) { this.sprite = sprite; this.isDirty = true; }
    public Sprite getSprite() { return sprite; }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}
