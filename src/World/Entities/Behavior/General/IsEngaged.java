package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;

public class IsEngaged extends AbstractBehavior {
    public IsEngaged(Blackboard bb) {
        super(bb);
    }

    @Override
    public AbstractBehavior duplicateWithNewBlackboard(Blackboard bb) {
        return new IsEngaged(bb);
    }

    @Override
    public int execute() {
        if(WorldController._instance.DEBUG_MODE) {
            System.out.println("Debug - Checking if mob is engaged");
        }
        return ((AbstractMob) this.bb.get("This")).isEngaged() ? 1 : 0;
    }
}
