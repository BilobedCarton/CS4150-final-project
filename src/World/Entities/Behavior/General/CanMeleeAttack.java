package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Player;

import java.awt.*;

public class CanMeleeAttack  extends AbstractBehavior {
    public CanMeleeAttack(Blackboard bb) {
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
        if(player != null) {
            return isPointAdjacent(new Point(mob.getX(), mob.getY()), new Point(player.getX(), player.getY())) ? 1 : 0;
        }
        return 0;
    }

    private boolean isPointAdjacent(Point a, Point b) {
        if(a.x == b.x) {
            return a.y == b.y - 1 || a.y == b.y + 1;
        }
        else if(a.y == b.y) {
            return a.x == b.x - 1 || a.x == b.x + 1;
        }
        return false;
    }
}
