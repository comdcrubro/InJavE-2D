package com.dcrubro.InJavE2D.object.components;

import com.dcrubro.InJavE2D.render.Texture;
import org.joml.Vector2f;

public class Sprite {

    private float width, height;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    private Texture texture = null;

    private Vector2f[] texCoords = {
            new Vector2f(1,1),
            new Vector2f(1,0),
            new Vector2f(0,0),
            new Vector2f(0,1)
    };

    public Texture getTexture() { return this.texture; }
    public Vector2f[] getTexCoords() { return this.texCoords; }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }


    public int getTexId() {
        return texture == null ? -1 : texture.getId();
    }
}
