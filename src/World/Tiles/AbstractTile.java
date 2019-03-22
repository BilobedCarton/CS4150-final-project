package World.Tiles;

import World.Effects.AbstractEffect;
import java.util.List;

public abstract class AbstractTile {
  private static int ID_COUNTER = 0;

  private final int ID;
  private List<AbstractEffect> effects;

  public AbstractTile(List<AbstractEffect> effects) {
    this.ID = ID_COUNTER++;
    this.effects = effects;
  }
}