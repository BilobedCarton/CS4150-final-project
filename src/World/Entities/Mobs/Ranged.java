package World.Entities.Mobs;

import World.Entities.AbstractMob;
import World.Entities.Behavior.BehaviorTreeLibrary;
import World.Entities.Behavior.Blackboard;

import java.awt.*;

public class Ranged extends AbstractMob {
    public Ranged(int x, int y) {
        super(x, y, 30, 2, Color.ORANGE);
        this.meleeDamage = 0;
        this.rangedDamage = 10;
    }

    @Override
    public void resetBehavior() {
        this.bb = new Blackboard();
        bb.put("This", this);
        this.behaviorTree = BehaviorTreeLibrary.generateSimpleRanged(bb);
    }
}
