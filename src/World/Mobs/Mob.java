package World.Mobs;

// The actual representation of a mob that exists in the level somewhere
// THIS CLASS IS A STUB
public class Mob {
  private int mobPrototypeID;
  private int x;
  private int y;
  private float health;

  public Mob(int x, int y, MobPrototype mobPrototype) {
    this.mobPrototypeID = mobPrototype.ID;
    this.x = x;
    this.y = y;
    this.health = mobPrototype.getMaxHealth();
  }
}
