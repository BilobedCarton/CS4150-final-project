package World.Collectibles;

import java.awt.*;

import World.WorldController;
import processing.core.PApplet;

public class HealthPotion extends CollectiblePrototype {
  private final double MAGNITUDE = 0.5;

  public HealthPotion() {
    super();
    // TODO: add health effect.
  }

  @Override
  public void draw(int x, int y) {
    PApplet sketch = WorldController._instance.sketch;
    int cellDimension = WorldController._instance.cellDimension;
    sketch.stroke(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue());
    sketch.fill(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue());
    sketch.ellipse(
            x * cellDimension + cellDimension / 2,
            y * cellDimension + cellDimension / 2,
            cellDimension - 2,
            cellDimension - 2);
    // TODO: add a white cross at center to make clear what this is.
  }
}
