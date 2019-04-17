package World.Entities;

import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;
import processing.core.PApplet;

import java.awt.*;

public abstract class AbstractMob extends AbstractEntity {
    protected AbstractBehavior behaviorTree;
    protected Blackboard bb;
    protected boolean isEngaged;
    protected int meleeDamage;
    protected int rangedDamage;
    // This is out of 5 - when we hit 5 we are ready to attack again.
    protected int reloadProgress;
    private static final int RELOAD_CAP = 5;

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

    public void resetReloadProgress() {
        this.reloadProgress = 0;
    }

    public void incrementReload() {
        this.reloadProgress++;
        this.reloadProgress = Math.min(this.reloadProgress, RELOAD_CAP);
    }

    public boolean canAttack() {
        return this.reloadProgress == RELOAD_CAP;
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

    public AbstractBehavior getBehaviorTree() {
        return behaviorTree;
    }

    @Override
    public void draw() {
        super.draw();
        if(WorldController._instance.DEBUG_MODE && this.isAlive()) {
            if (bb.getOrNull("TargetPoint") != null) {
                Point target = (Point) bb.get("TargetPoint");
                PApplet sketch = WorldController._instance.sketch;
                int cellDimension = WorldController._instance.cellDimension;
                sketch.fill(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
                sketch.stroke(this.getColor().getRed(), this.getColor().getGreen(), this.getColor().getBlue());
                sketch.ellipse(
                        target.x * cellDimension + (cellDimension / 2),
                        target.y * cellDimension + (cellDimension / 2),
                        cellDimension - 1,
                        cellDimension - 1);
            }
        }
    }

}
