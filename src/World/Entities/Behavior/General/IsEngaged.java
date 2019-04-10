package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;

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
        return ((AbstractMob) this.bb.get("This")).isEngaged() ? 1 : 0;
    }
}
