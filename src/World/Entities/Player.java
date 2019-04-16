package World.Entities;

import World.WorldController;

import java.awt.*;
import processing.core.PApplet;

public class Player extends AbstractEntity {
  public enum moveDirection {
    UP,
    LEFT,
    DOWN,
    RIGHT
  }

  public Player(int x, int y) {
    super(x, y, 100, 1, Color.WHITE);
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

      case 'f':
        fireWeapon();
        break;
      default:
        break;
    }
  }

  public void movePlayer(moveDirection dir) {
    int newX = this.getX();
    int newY = this.getY();
    switch(dir) {
      case UP:
        newY--;
        break;
      case LEFT:
        newX--;
        break;
      case DOWN:
        newY++;
        break;
      case RIGHT:
        newX++;
        break;
      default:
        break;
    }
    // check if trying to move into a wall.
    if(WorldController._instance.getTileTypeOfGivenTile(newX, newY).ID == 0) {
      return;
    }
    // check if trying to move into another entity.
    if(WorldController._instance.getEntitiesOnTile(newX, newY).size() > 0) {
      return;
    }
    else {
      this.move(newX, newY);
    }
  }


  public void fireWeapon() {
     PApplet sketch = WorldController._instance.sketch;

     int cellDimension = WorldController._instance.cellDimension;

     int mouseX = sketch.mouseX;
     int mouseY = sketch.mouseY;
     Projectiles bullet = new Projectiles(this.getX() * cellDimension + (cellDimension / 2),
             this.getY() * cellDimension + (cellDimension / 2),
             (mouseX - this.getX() * cellDimension + (cellDimension / 2)) / 10,
             (mouseY - this.getY() * cellDimension + (cellDimension / 2)) / 10);

     WorldController._instance.addBullet(bullet);
  }
}
