package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;

import java.awt.*;
import java.util.Arrays;

public class MoveAlongPath extends AbstractBehavior {
    public MoveAlongPath(Blackboard bb) {
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
            System.out.println("Debug - Moving mob along path to target point");
        }
        Point[] path = (Point[]) bb.get("Path");
        AbstractMob mob = (AbstractMob) bb.get("This");
        int i = mob.getSpeed();
        Point nextPoint;
        while(i > 0) {
            nextPoint = path[Math.max(path.length - i, 0)];
            if (WorldController._instance.getTileTypeOfGivenTile(nextPoint.x, nextPoint.y).ID == 0 || WorldController._instance.getEntitiesOnTile(nextPoint.x, nextPoint.y).size() > 0) {
                i--;
            }
            else {
                mob.move(nextPoint.x, nextPoint.y);
                bb.put("Path", Arrays.copyOfRange(path, 0, path.length - i));
                return 1;
            }
        }
        return 0;
    }
}
