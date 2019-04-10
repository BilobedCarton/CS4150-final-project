package World.Entities.Behavior.General;

import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Selector extends AbstractBehavior {
    List<AbstractBehavior> children;

    public Selector(Blackboard bb, AbstractBehavior... children) {
        super(bb);
        this.children = new ArrayList<AbstractBehavior>(Arrays.asList(children));
    }

    @Override
    public AbstractBehavior duplicateWithNewBlackboard(Blackboard bb) {
        for(int i = 0; i < children.size(); i++) {
            children.add(children.remove(0).duplicateWithNewBlackboard(bb));
        }
        this.bb = bb;
        return this;
    }

    @Override
    public int execute() {
        if(WorldController._instance.DEBUG_MODE) {
            System.out.println("Debug - Running a selector grouping");
        }
        for(AbstractBehavior child : children) {
            int flag = child.execute();
            if(flag != 0) {
                return flag;
            }
        }
        return 0;
    }
}
