package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Player;

public class MeleeAttack extends AbstractBehavior {
    public MeleeAttack(Blackboard bb) {
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
            player.changeHealth(-mob.getMeleeDamage());
            return 1;
        }
        return 0;
    }
}
