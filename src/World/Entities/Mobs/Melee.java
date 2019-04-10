package World.Entities.Mobs;

import World.Entities.AbstractMob;
import World.Entities.Behavior.Blackboard;
import World.WorldController;
import processing.core.PApplet;

import java.awt.*;

public class Melee extends AbstractMob {
    public Melee(int x, int y) {
        super(x, y, 50, 2, Color.pink);

    }

    @Override
    public void resetBehavior() {
        this.bb = new Blackboard();
        bb.put("This", this);
    }
}
