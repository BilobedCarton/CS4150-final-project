package World.Collectibles;

import java.util.ArrayList;
import java.util.List;

import World.Effects.AbstractEffect;
import World.WorldController;

public abstract class CollectiblePrototype {
  private static int ID_COUNTER = 0;

  private final int ID;
  private List<AbstractEffect> effectList;

  public CollectiblePrototype() {
    this.ID = ID_COUNTER;
    this.effectList = new ArrayList<>();
    ID_COUNTER++;
  }

  public static void reset() {
    ID_COUNTER = 0;
  }

  public int getID() {
    return ID;
  }

  public void placeInstance(int x, int y) {
    CollectibleInstance collectibleInstance = new CollectibleInstance(x, y, this);
    WorldController._instance.placeCollectible(collectibleInstance);
  }

  public void applyEffects(int x, int y) {
    for(AbstractEffect effect : effectList) {
      effect.applyEffect(x, y);
    }
  }

  public void addEffect(AbstractEffect effect) {
    this.effectList.add(effect);
  }

  public abstract void draw(int x, int y);
}
