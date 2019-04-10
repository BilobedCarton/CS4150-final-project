package World.Entities;

import World.WorldController;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

public class Player extends AbstractEntity {
  private boolean moveLeft, moveUp, moveRight, moveDown;

  public Player(int x, int y) {
    super(x, y, 100);
    this.currentSpeed = new PVector(0, 0);
    this.maxSpeed = 15;
    this.maxAcceleration = 12;
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
            this.getX(),
            this.getY(),
            cellDimension - 1,
            cellDimension - 1);
  }

  public void setMove(int k, boolean b) {
    switch (k) {
      case 87:
        moveUp = b;
        break;
      case 83:
        moveDown = b;
        break;
      case 68:
        moveRight = b;
        break;
      case 65:
        moveLeft = b;
        break;
      default:
        return;
    }
  }

  public void movePlayer() {
    int x = this.getX() + this.maxSpeed * ((this.moveRight ? 1 : 0) - (this.moveLeft ? 1 : 0));
    int y = this.getY() + this.maxSpeed * ((this.moveDown ? 1 : 0) - (this.moveUp ? 1 : 0));
    if(WorldController._instance.getTileTypeOfGivenTile(this.getX() / WorldController._instance.cellDimension, this.getY() / WorldController._instance.cellDimension).ID == 0) {
      return;
    }
    else {
      this.move(x, y);
    }
  }
}
