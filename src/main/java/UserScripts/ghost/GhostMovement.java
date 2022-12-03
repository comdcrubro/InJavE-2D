package UserScripts.ghost;

import com.dcrubro.InJavE2D.object.GameObject;
import com.dcrubro.InJavE2D.scene.Scene;
import com.dcrubro.InJavE2D.scripting.InJavEScript;

public class GhostMovement extends InJavEScript {

    private GameObject ghost;
    private Scene currentScene;

    private float moveSpeed = 200f;
    private float movedUnits;
    private float maxX = 1190f;
    private float minX = -20f;
    private float maxY = 550f;
    private float minY = 0f;

    public GhostMovement(Scene currentScene, GameObject ghost) {
        super(currentScene, ghost);
        this.currentScene = currentScene;
        this.ghost = ghost;
    }

    @Override
    public void start() {
        movedUnits = 0f;
    }

    @Override
    public void update(float deltaTime) {
        /*if (ghost.transform.position.x < maxX)
            ghost.transform.position.x += moveSpeed * deltaTime;
        else if (ghost.transform.position.x >)*/
    }
}
