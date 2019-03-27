package World.Entities;

public abstract class AbstractEntity {
  private int x;
  private int y;
  private int maxHealth;
  protected int health;
  protected double maxSpeed;
  protected double maxAcceleration;
  protected double maxRotation;
  protected double maxRotationalAcceleration;

  protected boolean isAlive;

  AbstractEntity(int x, int y, int maxHealth) {
    this.x = x;
    this.y = y;
    this.maxHealth = maxHealth;
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

  public void move(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void takeDamage(int damage) {
    this.health -= damage;
    if(this.health <= 0) {
      this.isAlive = false;
    }
  }

  public void healDamage(int healing) {
    this.health += healing;
    this.health = Math.min(this.health, this.maxHealth);
  }

  public abstract void draw();
}
