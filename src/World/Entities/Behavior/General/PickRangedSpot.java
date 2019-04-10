package World.Entities.Behavior.General;

import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Utilities.RayToPlayer;
import World.WorldController;

import java.awt.*;

public class PickRangedSpot extends AbstractBehavior {
    public PickRangedSpot(Blackboard bb) {
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
            System.out.println("Debug - Picking spot to shoot player from");
        }
        Point[] path = (Point[]) bb.get("Path");
        for(Point p : path) {
            RayToPlayer rayToPlayer = new RayToPlayer(p);
            if(rayToPlayer.canSeePlayerFromPoint() && WorldController._instance.getEntitiesOnTile(p.x, p.y).size() == 0) {
                bb.put("TargetPoint", p);
                return 1;
            }
        }
        return 0;
    }
}
