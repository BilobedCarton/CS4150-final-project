package World.Collectibles;

import java.awt.*;

import World.Effects.WinEffect;
import World.WorldController;
import processing.core.PApplet;

public class Treasure extends CollectiblePrototype {
  public Treasure() {
    super();
    this.addEffect(new WinEffect());
  }

  @Override
  public void draw(int x, int y) {
    PApplet sketch = WorldController._instance.sketch;
    int cellDimension = WorldController._instance.cellDimension;
    sketch.stroke(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue());
    sketch.fill(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue());
    sketch.ellipse(
            x * cellDimension + cellDimension / 2,
            y * cellDimension + cellDimension / 2,
            cellDimension - 2,
            cellDimension - 2);

  }
}
