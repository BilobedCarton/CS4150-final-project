package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Projectile;
import World.WorldController;
import processing.core.PVector;

public class Shoot extends AbstractBehavior {
    public Shoot(Blackboard bb) {
        super(bb);
    }

    @Override
    public AbstractBehavior duplicateWithNewBlackboard(Blackboard bb) {
        this.bb = bb;
        return this;
    }

    @Override
    public int execute() {
        int cellDimension = WorldController._instance.cellDimension;
        AbstractMob mob = (AbstractMob) bb.get("This");
        PVector facingVector = (new PVector(cellDimension, 0)).rotate(mob.getOrientation());
        Projectile projectile = new Projectile(mob.getX() * cellDimension + (cellDimension / 2) + (int)facingVector.x,
                mob.getY() * cellDimension + (cellDimension / 2) + (int)facingVector.y, mob.getOrientation(), mob.getRangedDamage());
        WorldController._instance.addProjectile(projectile);
        mob.resetReloadProgress();
        return 1;
    }
}
