package World.Entities.Behavior;

public abstract class AbstractBehavior {
    protected Blackboard bb;

    public AbstractBehavior(Blackboard bb) {
        this.bb = bb;
    }

    public abstract AbstractBehavior duplicateWithNewBlackboard(Blackboard bb);

    public abstract int execute(); // returns FAIl = 0, SUCCESS = 1, 2 is a special case that signifies the entity has done all it can this tick (can't move and shoot in the same tick, etc.)
}
