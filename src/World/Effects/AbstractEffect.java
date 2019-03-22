package World.Effects;

public abstract class AbstractEffect {
  private static int ID_COUNTER = 0;

  private final int ID;

  public AbstractEffect() {
    this.ID = ID_COUNTER++;
  }
}