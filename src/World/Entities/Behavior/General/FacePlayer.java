package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;
import processing.core.PVector;

public class FacePlayer extends AbstractBehavior {

    public FacePlayer(Blackboard bb) {
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
            System.out.println("Debug - Facing player");
        }
        AbstractMob mob = (AbstractMob) bb.get("This");
        PVector vectorToPlayer =
                (new PVector(WorldController._instance.player.getX(), WorldController._instance.player.getY()))
                        .sub(new PVector(mob.getX(), mob.getY()));
        if(mob.getOrientation() == vectorToPlayer.heading()) {
            return 1;
        }
        else {
            mob.changeOrientation(vectorToPlayer.heading());
            return 2;
        }
    }
}
