package World.Obstacles;

public abstract class ObstaclePrototype {
  private static int ID_COUNTER = 0;

  private final int ID;

  public ObstaclePrototype() {
    this.ID = ID_COUNTER;
    ID_COUNTER++;
  }

  public static void reset() {
    ID_COUNTER = 0;
  }

  public int getID() {
    return ID;
  }
}