package World.Entities;


import World.WorldController;
import processing.core.PApplet;

import java.awt.*;

public abstract class AbstractEntity {
  private int x;
  private int y;
  private int maxHealth;
  private int health;
  private int speed;
  private float orientation;
  private boolean isAlive;
  private Color color;

  AbstractEntity(int x, int y, int maxHealth, int speed, Color color) {
    this.x = x;
    this.y = y;
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    this.speed = speed;
    this.isAlive = true;
    this.color = color;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getHealth() {
    return health;
  }

  public int getSpeed() {
    return speed;
  }

  public float getOrientation() {
    return orientation;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public Color getColor() {
    return color;
  }

  public void move(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void changeHealth(int delta) {
    this.health += delta;
    this.health = Math.min(this.health, this.maxHealth);
    if(this.health <= 0) {
      this.isAlive = false;
    }
  }

  public void changeOrientation(float orientation) {
    this.orientation = orientation;
  }

  public void draw() {
    if(this.isAlive) {
      PApplet sketch = WorldController._instance.sketch;
      int cellDimension = WorldController._instance.cellDimension;
      sketch.stroke(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
      sketch.fill(color.getRed(), color.getGreen(), color.getBlue());
      sketch.ellipse(
              this.getX() * cellDimension + (cellDimension / 2),
              this.getY() * cellDimension + (cellDimension / 2),
              cellDimension - 1,
              cellDimension - 1);
    }
  }
}
