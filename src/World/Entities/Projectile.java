
package World.Entities;

import World.WorldController;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

public class Projectile {
  private static int ID_COUNTER = 0;
  private final static int speed = 2;

  private int currentX;
  private int currentY;
  private float heading;
  private int damage;
  private final int ID;

  private boolean hitSomething;

  Projectile(int currentX, int currentY, float heading, int damage) {
    this.currentX = currentX;
    this.currentY = currentY;
    this.heading = heading;
    this.damage = damage;
    this.ID = ID_COUNTER++;
    hitSomething = false;
  }

  public void draw() {
    PApplet sketch = WorldController._instance.sketch;
    sketch.stroke(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
    sketch.fill(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
    sketch.ellipse(currentX, currentY, 5, 5);
  }

  public int getCurrentX() {
    return currentX;
  }

  public int getCurrentY() {
    return currentY;
  }

  public void update() {
    int cellDimension = WorldController._instance.cellDimension;
    PVector velocity = (new PVector(cellDimension, 0)).rotate(heading);
    for(int i = 0; i < speed; i++) {
      if(this.checkHitSomething()) {
        this.applyHit();
        return;
      }
    }
    this.currentX += velocity.x;
    this.currentY += velocity.y;
  }

  public boolean hitSomething() {
    return this.hitSomething;
  }

  @Override
  public boolean equals(Object obj) {
    if((obj instanceof Projectile) == false) {
      return false;
    }
    Projectile p = (Projectile) obj;
    return this.ID == p.ID;
  }

  private boolean checkHitSomething() {
    Point tileCoords = WorldController._instance.getPointAtPixelCoordinates(getCurrentX(), getCurrentY());
    if((WorldController._instance.getEntitiesOnTile(tileCoords.x, tileCoords.y).size() > 0) || (WorldController._instance.getTileTypeOfGivenTile(tileCoords.x, tileCoords.y).ID == 0)) {
      this.hitSomething = true;
    }
    return this.hitSomething;
  }

  private void applyHit() {
    Point tileCoords = WorldController._instance.getPointAtPixelCoordinates(getCurrentX(), getCurrentY());
    for(AbstractEntity ae : WorldController._instance.getEntitiesOnTile(tileCoords.x, tileCoords.y)) {
      ae.changeHealth(-damage);
    }
  }
}
