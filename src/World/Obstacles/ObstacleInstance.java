package World.Obstacles;

public class ObstacleInstance {
  private int obstaclePrototypeID;
  private int x;
  private int y;

  public ObstacleInstance(int x, int y, ObstaclePrototype obstaclePrototype) {
    this.x = x;
    this.y = y;
    this.obstaclePrototypeID = obstaclePrototype.getID();
  }
}
