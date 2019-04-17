package World.Entities.Behavior.General;

import World.Effects.AbstractEffect;
import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Player;
import World.Tiles.TileType;
import World.WorldController;

import java.awt.*;
import java.util.ArrayList;

public class Dodge extends AbstractBehavior {
    public Dodge(Blackboard bb) {
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
        ArrayList<Point> choices = new ArrayList<>();
        Point[] possibleMoves = {
                new Point(mob.getX() - 1, mob.getY()),
                new Point(mob.getX(), mob.getY() - 1),
                new Point(mob.getX() + 1, mob.getY()),
                new Point(mob.getX(), mob.getY() + 1)};
        for(Point p : possibleMoves) {
            if(WorldController._instance.getTileTypeOfGivenTile(p.x, p.y).ID != 0 && WillBeHitByBullet.wouldBeHitByBulletAtPoint(p) == false) {
                choices.add(p);
            }
        }
        if(choices.size() == 0) {
            return 0;
        }
        Point bestChoice = choices.get(0);
        for(Point choice : choices) {
            if(heuristicCostEstimate(choice, playerLocation) < heuristicCostEstimate(bestChoice, playerLocation)) {
                bestChoice = choice;
            }
        }
        mob.move(bestChoice.x, bestChoice.y);
        return 1;
    }

    private int heuristicCostEstimate(Point start, Point goal) {
        // Base cost is the simple distance to goal from start.
        int cost = Math.abs(goal.x - start.x) + Math.abs(goal.y - start.y);
        // If our health would be changed by stepping onto this point we should consider this as well. Healing is good.
        cost += getChangeInHealthFromTile(start);
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
