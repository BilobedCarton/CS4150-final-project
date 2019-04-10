package World.Entities.Behavior;

public abstract class AbstractBehavior {
    protected Blackboard bb;

    public AbstractBehavior(Blackboard bb) {
        this.bb = bb;
    }

    public abstract AbstractBehavior duplicateWithNewBlackboard(Blackboard bb);

    public abstract int execute(); // returns FAIl = 0, SUCCESS = 1
}
