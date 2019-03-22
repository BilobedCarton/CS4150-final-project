package World.Obstacles;

public abstract class AbstractObstacle {
  private static int ID_COUNTER = 0;

  private final int ID;

  public AbstractObstacle() {
    this.ID = ID_COUNTER++;
  }
}