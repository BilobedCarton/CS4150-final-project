package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;

import java.awt.*;

public class PickMeleeSpot extends AbstractBehavior {
    public PickMeleeSpot(Blackboard bb) {
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
            System.out.println("Debug - Picking a spot as close to adjacent to the player as possible");
        }
        AbstractMob mob = (AbstractMob) bb.get("This");
        Point[] path = (Point[]) bb.get("Path");
        for(int i = 0; i < path.length; i++) {
            Point closestPoint = new Point(path[i].x - 1, path[i].y);
            for(int j = 0; j < 4; j++) {
                Point pt;
                if(j == 0) {
                    pt = new Point(path[i].x - 1, path[i].y);
                }
                else if(j == 1) {
                    pt = new Point(path[i].x, path[i].y - 1);
                }
                else if(j == 2) {
                    pt = new Point(path[i].x + 1, path[i].y);
                }
                else {
                    pt = new Point(path[i].x, path[i].y + 1);
                }
                if((WorldController._instance.getEntitiesOnTile(pt.x, pt.y).size() == 0
                        && WorldController._instance.getTileTypeOfGivenTile(pt.x, pt.y).ID != 0)
                        ||(WorldController._instance.getEntitiesOnTile(pt.x, pt.y).size() == 1
                        && WorldController._instance.getEntitiesOnTile(pt.x, pt.y).get(0).equals(mob))) {
                    if(getDistanceToPoint(mob, closestPoint) > getDistanceToPoint(mob, pt)) {
                        closestPoint = pt;
                    }
                }
            }
            if((WorldController._instance.getEntitiesOnTile(closestPoint.x, closestPoint.y).size() == 0
                        && WorldController._instance.getTileTypeOfGivenTile(closestPoint.x, closestPoint.y).ID != 0)
                    ||(WorldController._instance.getEntitiesOnTile(closestPoint.x, closestPoint.y).size() == 1
                        && WorldController._instance.getEntitiesOnTile(closestPoint.x, closestPoint.y).get(0).equals(mob))) {
                bb.put("TargetPoint", closestPoint);
                return 1;
            }
        }
        return 0;
    }

    private int getDistanceToPoint(AbstractMob mob, Point target) {
        return Math.abs(target.x - mob.getX()) + Math.abs(target.y - mob.getY());
    }
}
