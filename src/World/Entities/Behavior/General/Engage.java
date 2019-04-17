package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;

public class Engage extends AbstractBehavior {
    public Engage(Blackboard bb) {
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
            System.out.println("Debug - Setting engagement to true for mob");
        }
        ((AbstractMob) this.bb.get("This")).engage();
        this.bb.put("Enemy", WorldController._instance.player);
        return 1;
    }
}
