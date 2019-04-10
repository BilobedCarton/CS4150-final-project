package World.Entities.Behavior.General;

import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;

public class HasPathToPlayer extends AbstractBehavior {
    public HasPathToPlayer(Blackboard bb) {
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
            System.out.println("Debug - Checking path to player");
        }
        return this.bb.getOrNull("Path") == null ? 0 : 1;
    }
}
