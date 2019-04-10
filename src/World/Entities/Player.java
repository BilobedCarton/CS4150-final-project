package World.Entities;

import World.WorldController;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

public class Player extends AbstractEntity {
  public enum moveDirection {
    UP,
    LEFT,
    DOWN,
    RIGHT,
    NONE
  }

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
    sketch.stroke(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
    sketch.fill(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
    sketch.ellipse(
            this.getX() * cellDimension + (cellDimension / 2),
            this.getY() * cellDimension + (cellDimension / 2),
            cellDimension - 1,
            cellDimension - 1);
  }

  public void parseInput(int keyCode) {
    switch(keyCode) {
      case 'w':
        movePlayer(moveDirection.UP);
        break;
      case 'a':
        movePlayer(moveDirection.LEFT);
        break;
      case 's':
        movePlayer(moveDirection.DOWN);
        break;
      case 'd':
        movePlayer(moveDirection.RIGHT);
        break;
      default:
        break;
    }
  }

  public void movePlayer(moveDirection dir) {
    switch(dir) {
      case UP:
        move(this.getX(), this.getY() - 1);
        break;
      case LEFT:
        move(this.getX() - 1, this.getY());
        break;
      case DOWN:
        move(this.getX(), this.getY() + 1);
        break;
      case RIGHT:
        move(this.getX() + 1, this.getY());
        break;
      default:
        break;
    }
  }
}
