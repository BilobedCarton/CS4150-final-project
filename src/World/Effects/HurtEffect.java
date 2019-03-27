package World.Effects;

import java.util.List;

import World.Entities.AbstractEntity;
import World.WorldController;

public class HurtEffect extends AbstractEffect {
  private final int MAGNITUDE;

  public HurtEffect(int magnitude) {
    this.MAGNITUDE = magnitude;
  }

  @Override
  public void applyEffect(int x, int y) {
    List<AbstractEntity> entities = WorldController._instance.getEntitiesOnTile(x, y);
    for(AbstractEntity entity : entities) {
      entity.takeDamage(MAGNITUDE);
    }
  }
}
