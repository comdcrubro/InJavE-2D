package com.dcrubro.InJavE2D.object.components;

import com.dcrubro.InJavE2D.object.Component;
import com.dcrubro.InJavE2D.util.Debug;

public class FontRenderer extends Component {

    @Override
    public void start() {
        if (gameObject.getComponent(SpriteRenderer.class) != null) {
            Debug.log("(FontRenderer) Started.");
            Debug.log("(FontRenderer) Found FontRenderer!");
        }
    }

    @Override
    public void update(float deltaTime) {

    }
}
