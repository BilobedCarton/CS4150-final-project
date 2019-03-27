package World.Effects;

public abstract class AbstractEffect {
  private static int ID_COUNTER = 0;

  private final int ID;

  public AbstractEffect() {
    this.ID = ID_COUNTER++;
  }

  public static void reset() {
    ID_COUNTER = 0;
  }

  // Applies the effect to the agents in the given location.
  public abstract void applyEffect(int x, int y);
}