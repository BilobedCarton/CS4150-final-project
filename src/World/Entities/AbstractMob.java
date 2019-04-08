package World.Entities;

import World.Entities.Behavior.AbstractBehavior;

public abstract class AbstractMob extends AbstractEntity {
    protected AbstractBehavior behaviorTree;
    protected int meleeDamage;
    protected int rangedDamage;

    public AbstractMob(int x, int y, int maxHealth) {
        super(x, y, maxHealth);
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
}
