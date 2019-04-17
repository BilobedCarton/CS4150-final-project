package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Projectile;
import World.WorldController;
import processing.core.PVector;

import java.awt.*;

public class WillBeHitByBullet extends AbstractBehavior {
    public WillBeHitByBullet(Blackboard bb) {
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
        return wouldBeHitByBulletAtPoint(new Point(mob.getX(), mob.getY())) ? 1 : 0;
    }

    public static boolean wouldBeHitByBulletAtPoint(Point p) {
        Projectile[] projectiles = WorldController._instance.getProjectiles();
        for(Projectile pr : projectiles) {
            Point[] path = new Point[1 + Projectile.getSpeed()];
            PVector velocity = (new PVector(WorldController._instance.cellDimension, 0)).rotate(pr.getHeading());
            path[0] = WorldController._instance.getPointAtPixelCoordinates(pr.getCurrentX(), pr.getCurrentY());
            path[1] = WorldController._instance.getPointAtPixelCoordinates((int)(pr.getCurrentX() + velocity.x), (int)(pr.getCurrentY() + velocity.y));
            velocity = velocity.mult(2);
            path[2] = WorldController._instance.getPointAtPixelCoordinates((int)(pr.getCurrentX() + velocity.x), (int)(pr.getCurrentY() + velocity.y));
            for(int i = 0; i < path.length; i++) {
                if(path[i].equals(p)) {
                    return true;
                }
            }
        }
        return false;
    }
}
