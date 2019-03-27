package World.Entities;

public class Player extends AbstractEntity {
  public Player(int x, int y) {
    super(x, y);
    this.health = 100;
    this.maxSpeed = 1;
    this.maxAcceleration = 1.5;
    this.maxRotation = Math.PI / 4;
    this.maxRotationalAcceleration = Math.PI / 3;
  }

  @Override
  public void draw() {

  }
}
