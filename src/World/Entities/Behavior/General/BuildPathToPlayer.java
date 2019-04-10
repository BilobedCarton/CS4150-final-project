package World.Entities.Behavior.General;

import World.Entities.AbstractMob;
import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.Entities.Utilities.AStarPath;
import World.WorldController;

public class BuildPathToPlayer extends AbstractBehavior {
    public BuildPathToPlayer(Blackboard bb) {
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
            System.out.println("Debug - building path to player.");
        }

        AbstractMob mob = (AbstractMob) bb.get("This");
        AStarPath path = new AStarPath(mob, WorldController._instance.player);
        if(path.succeeded) {
            bb.put("Path", path.path);
            return 1;
        }
        bb.put("Path", null);
        return 0;
    }
}
