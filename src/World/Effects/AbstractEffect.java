package World.Effects;

public abstract class AbstractEffect {

  public AbstractEffect() {}

  // Applies the effect to the agents in the given location.
  public abstract void applyEffect(int x, int y);
}