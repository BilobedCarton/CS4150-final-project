package World.Entities.Mobs;

import World.Entities.AbstractMob;
import World.Entities.Behavior.BehaviorTreeLibrary;
import World.Entities.Behavior.Blackboard;

import java.awt.*;

public class Melee extends AbstractMob {
    public Melee(int x, int y) {
        super(x, y, 50, 2, Color.pink);
        this.meleeDamage = 5;
        this.rangedDamage = 0;
    }

    @Override
    public void resetBehavior() {
        this.bb = new Blackboard();
        bb.put("This", this);
        this.behaviorTree = BehaviorTreeLibrary.generateSimpleMelee(bb);
    }
}
