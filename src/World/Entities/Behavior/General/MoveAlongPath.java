package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Utilities.AStarPath;
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
        Point[] path = (Point[]) bb.get("Path");
        AbstractMob mob = (AbstractMob) bb.get("This");
        if(path.length <= 5) {
            AStarPath newPath = new AStarPath(new Point(mob.getX(), mob.getY()), (Point) bb.get("TargetPoint"));
            path = newPath.path;
        }
        int i = mob.getSpeed();
        Point nextPoint;
        while(i > 0) {
            nextPoint = path[i];
            if (WorldController._instance.getTileTypeOfGivenTile(nextPoint.x, nextPoint.y).ID == 0 || WorldController._instance.getEntitiesOnTile(nextPoint.x, nextPoint.y).size() > 0) {
                i--;
            }
            else {
                mob.move(nextPoint.x, nextPoint.y);
                bb.put("Path", Arrays.copyOfRange(path, i, path.length));
                return 2;
            }
        }
        return 0;
    }
}
