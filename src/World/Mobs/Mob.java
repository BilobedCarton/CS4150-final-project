package World.Mobs;

public class Mob {
  private int mobPrototypeID;
  private int x;
  private int y;

  public Mob(int x, int y, MobPrototype mobPrototype) {
    this.mobPrototypeID = mobPrototype.ID;
    this.x = x;
    this.y = y;
  }
}
