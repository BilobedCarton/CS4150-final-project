package World.Effects;

import java.util.List;

import World.Entities.AbstractEntity;
import World.WorldController;

public class HurtEffect extends AbstractEffect {
  private int magnitude;

  public HurtEffect(int magnitude) {
    this.magnitude = magnitude;
  }

  @Override
  public void applyEffect(int x, int y) {
    List<AbstractEntity> entities = WorldController._instance.getEntitiesOnTile(x, y);
    for(AbstractEntity entity : entities) {
      entity.changeHealth(-magnitude);
    }
  }

  @Override
  public boolean effectsHealth() {
    return true;
  }

  @Override
  public int getMagnitude() {
    return -magnitude;
  }
}
