package World.Entities;

import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;

public abstract class AbstractMob extends AbstractEntity {
    protected AbstractBehavior behaviorTree;
    protected Blackboard bb;
    protected boolean isEngaged;
    protected int meleeDamage;
    protected int rangedDamage;

    public AbstractMob(int x, int y, int maxHealth) {
        super(x, y, maxHealth);
        this.isEngaged = false;
        this.bb = new Blackboard();
        this.bb.put("This", this);
        this.resetBehavior();
    }

    public void setBehavior(AbstractBehavior behavior) {
        this.behaviorTree = behavior;
    }

    public abstract void resetBehavior();

    public int getMeleeDamage() {
        return meleeDamage;
    }

    public int getRangedDamage() {
        return rangedDamage;
    }

    public boolean isEngaged() {
        return isEngaged;
    }

    public void engage() {
        this.isEngaged = true;
    }
}
