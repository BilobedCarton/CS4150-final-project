package World.Entities;


public abstract class AbstractEntity {
  private int x;
  private int y;
  private int maxHealth;
  protected int health;
  protected int speed;
  protected double maxRotation;
  protected double maxRotationalAcceleration;

  protected boolean isAlive;

  AbstractEntity(int x, int y, int maxHealth) {
    this.x = x;
    this.y = y;
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    this.isAlive = true;
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

  public abstract void draw();
}
