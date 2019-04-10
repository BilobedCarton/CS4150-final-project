package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;

import java.awt.*;

public class IsAdjacentToPlayer extends AbstractBehavior {
    public IsAdjacentToPlayer(Blackboard bb) {
        super(bb);
    }

    @Override
    public AbstractBehavior duplicateWithNewBlackboard(Blackboard bb) {
        this.bb = bb;
        return this;
    }

    @Override
    public int execute() {
        if(WorldController._instance.DEBUG_MODE) {
            System.out.println("Debug - Checking if adjacent to player");
        }
        AbstractMob mob = (AbstractMob) bb.get("This");
        if ((Math.abs(WorldController._instance.player.getX() - mob.getX()) + Math.abs(WorldController._instance.player.getY() + mob.getY())) == 1) {
            bb.put("TargetPoint", new Point(mob.getX(), mob.getY()));
            bb.put("Path", null);
            return 1;
        }
        return 0;
    }
}
