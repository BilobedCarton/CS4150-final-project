package World.Collectibles;

import World.WorldController;

public class CollectibleInstance {
  private int collectablePrototypeID;
  private int x;
  private int y;

  public CollectibleInstance(int x, int y, CollectiblePrototype collectiblePrototype) {
    this.x = x;
    this.y = y;
    this.collectablePrototypeID = collectiblePrototype.getID();
  }

  public void draw() {
    WorldController._instance.collectiblePrototypes.get(collectablePrototypeID).draw(x, y);
  }
}
