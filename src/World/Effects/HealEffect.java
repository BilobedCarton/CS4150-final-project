package World.Effects;

import java.util.List;

import World.Entities.AbstractEntity;
import World.WorldController;

public class HealEffect extends AbstractEffect {
  private int magnitude;

  public HealEffect(int magnitude) {
    this.magnitude = magnitude;
  }

  @Override
  public void applyEffect(int x, int y) {
    List<AbstractEntity> entities = WorldController._instance.getEntitiesOnTile(x, y);
    for(AbstractEntity entity : entities) {
      entity.changeHealth(magnitude);
    }
  }
}
