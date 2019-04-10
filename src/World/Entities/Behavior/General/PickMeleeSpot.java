package World.Entities.Behavior.General;

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
        Point[] path = (Point[]) bb.get("Path");
        for(int i = 0; i < path.length; i++) {
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
                if(WorldController._instance.getEntitiesOnTile(pt.x, pt.y).size() == 0 && WorldController._instance.getTileTypeOfGivenTile(pt.x, pt.y).ID != 0) {
                    bb.put("TargetPoint", pt);
                    return 1;
                }
                else if(WorldController._instance.getEntitiesOnTile(pt.x, pt.y).size() == 1 && WorldController._instance.getEntitiesOnTile(pt.x, pt.y).get(0).equals(bb.get("This"))) {
                    bb.put("TargetPoint", pt);
                    return 1;
                }
            }
        }
        return 0;
    }
}
