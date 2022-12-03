package UserScripts.Pong;

import com.dcrubro.InJavE2D.object.GameObject;
import com.dcrubro.InJavE2D.scene.Scene;
import com.dcrubro.InJavE2D.scripting.InJavEScript;

public class Peddel1 extends InJavEScript {

    private GameObject ghost;
    private Scene currentScene;

    public Peddel1(Scene currentScene, GameObject ghost) {
        super(currentScene, ghost);
        this.ghost = ghost;
        this.currentScene = currentScene;
    }
}
