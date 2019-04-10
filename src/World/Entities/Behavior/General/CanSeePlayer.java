package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Utilities.RayToPlayer;
import World.WorldController;

import java.awt.*;

public class CanSeePlayer extends AbstractBehavior {

    public CanSeePlayer(Blackboard bb) {
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
            System.out.println("Debug - Checking if mob can see Player");
        }
        AbstractMob mob = (AbstractMob) this.bb.get("This");
        RayToPlayer rayCast = new RayToPlayer(new Point(mob.getX(), mob.getY()));
        return rayCast.canSeePlayerFromPoint() ? 1 : 0;
    }
}
