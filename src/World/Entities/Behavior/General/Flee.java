package World.Entities.Behavior.General;

import World.Effects.AbstractEffect;
import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Player;
import World.Tiles.TileType;
import World.WorldController;

import java.awt.*;

public class Flee extends AbstractBehavior{
    public Flee(Blackboard bb) {
        super(bb);
    }

    @Override
    public AbstractBehavior duplicateWithNewBlackboard(Blackboard bb) {
        this.bb = bb;
        return this;
    }

    @Override
    public int execute() {
        AbstractMob mob = (AbstractMob) bb.get("This");
        Player player = (Player) bb.getOrNull("Enemy");
        Point playerLocation = new Point(player.getX(), player.getY());
        if(player != null) {
            Point farthestPointFromPlayer = new Point(mob.getX() - 1, mob.getY());
            for(int i = 0; i < 4; i++) {
                Point p;
                if(i == 0) {
                    p = new Point(mob.getX() - 1, mob.getY());
                }
                else if (i == 1) {
                    p = new Point(mob.getX(), mob.getY() - 1);
                }
                else if (i == 2) {
                    p = new Point(mob.getX() + 1, mob.getY());
                }
                else {
                    p = new Point(mob.getX(), mob.getY() + 1);
                }
                if(heuristicCostEstimate(p, playerLocation) > heuristicCostEstimate(farthestPointFromPlayer, playerLocation)
                        && WorldController._instance.getTileTypeOfGivenTile(p.x, p.y).ID != 0) {
                    farthestPointFromPlayer = p;
                }
            }
            if( WorldController._instance.getTileTypeOfGivenTile(farthestPointFromPlayer.x, farthestPointFromPlayer.y).ID != 0) {
                mob.move(farthestPointFromPlayer.x, farthestPointFromPlayer.y);
            }
        }
        return 0;
    }

    private int heuristicCostEstimate(Point start, Point goal) {
        // Base cost is the simple distance to goal from start.
        int cost = Math.abs(goal.x - start.x) + Math.abs(goal.y - start.y);
        // If our health would be changed by stepping onto this point we should consider this as well. Healing is good.
        cost -= getChangeInHealthFromTile(start);
        return cost;
    }

    private int getChangeInHealthFromTile(Point pt) {
        TileType tileType =  WorldController._instance.getTileTypeOfGivenTile(pt.x, pt.y);
        int change = 0;
        for(AbstractEffect ae : tileType.getEffects()) {
            if(ae.effectsHealth()) {
                change += ae.getMagnitude();
            }
        }
        return change;
    }
}
