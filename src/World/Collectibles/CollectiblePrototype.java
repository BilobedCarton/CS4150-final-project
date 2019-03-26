package World.Collectibles;

import World.WorldController;

public abstract class CollectiblePrototype {
  private static int ID_COUNTER = 0;

  private final int ID;

  public CollectiblePrototype() {
    this.ID = ID_COUNTER;
    ID_COUNTER++;
  }

  public int getID() {
    return ID;
  }

  public void placeInstance(int x, int y) {
    CollectibleInstance collectibleInstance = new CollectibleInstance(x, y, this);
    WorldController._instance.placeCollectible(collectibleInstance);
  }

  public abstract void draw();
}
