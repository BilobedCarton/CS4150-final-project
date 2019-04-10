package World.Entities;

import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;

import java.awt.*;

public abstract class AbstractMob extends AbstractEntity {
    protected AbstractBehavior behaviorTree;
    protected Blackboard bb;
    protected boolean isEngaged;
    protected int meleeDamage;
    protected int rangedDamage;

    public AbstractMob(int x, int y, int maxHealth, int speed, Color color) {
        super(x, y, maxHealth, speed, color);
        this.isEngaged = false;
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

    public int executeBehavior() {
        return behaviorTree.execute();
    }
}
