package World.Entities.Behavior.General;

import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;

public class Aim extends AbstractBehavior {
    public Aim(Blackboard bb) {
        super(bb);
    }

    @Override
    public AbstractBehavior duplicateWithNewBlackboard(Blackboard bb) {
        return null;
    }

    @Override
    public int execute() {
        return 0;
    }
}