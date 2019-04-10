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
