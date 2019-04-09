package World.Entities;

import World.WorldController;
import processing.core.PApplet;

import java.awt.*;

public class Player extends AbstractEntity {
  public Player(int x, int y) {
    super(x, y, 100);
    this.maxSpeed = 1;
    this.maxAcceleration = 1.5;
    this.maxRotation = Math.PI / 4;
    this.maxRotationalAcceleration = Math.PI / 3;
  }

  @Override
  public void draw() {
    PApplet sketch = WorldController._instance.sketch;
    int cellDimension = WorldController._instance.cellDimension;
    sketch.stroke(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue());
    sketch.fill(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue());
    sketch.ellipse(
            this.getX() * cellDimension + 1,
            this.getY() * cellDimension + 1,
            cellDimension - 1,
            cellDimension - 1);
  }
}
