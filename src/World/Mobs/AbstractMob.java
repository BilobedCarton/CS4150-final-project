package World.Mobs;

public abstract class AbstractMob {
  private static int ID_COUNTER = 0;

  private final int ID;

  public AbstractMob() {
    this.ID = ID_COUNTER++;
  }
}