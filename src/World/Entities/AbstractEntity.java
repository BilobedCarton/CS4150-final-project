package World.Entities;

import processing.core.PVector;

public abstract class AbstractEntity {
  private int x;
  private int y;
  private int maxHealth;
  protected int health;
  protected int maxSpeed;
  protected PVector currentSpeed; // this is for storing the modified speed.
  protected float maxAcceleration;
  protected double maxRotation;
  protected double maxRotationalAcceleration;

  protected boolean isAlive;
  private boolean isSpeedModified;
  private double speedModifier;

  AbstractEntity(int x, int y, int maxHealth) {
    this.x = x;
    this.y = y;
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    this.isAlive = true;
    this.isSpeedModified = false;
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

  public void modifySpeed(double multiplier) {
    if(isSpeedModified == false) {
      this.maxSpeed *= multiplier;
      this.isSpeedModified = true;
      this.speedModifier = multiplier;
    }
  }

  public void resetSpeed() {
    this.maxSpeed /= this.speedModifier;
    this.isSpeedModified = false;
  }

  public abstract void draw();
}
