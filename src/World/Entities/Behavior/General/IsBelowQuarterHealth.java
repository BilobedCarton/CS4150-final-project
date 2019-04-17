package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;

public class IsBelowQuarterHealth extends AbstractBehavior {
    public IsBelowQuarterHealth(Blackboard bb) {
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
        return mob.getHealth() < mob.getMaxHealth() / 4 ? 1 : 0;
    }
}
