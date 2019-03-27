package World.Entities;

// The actual representation of a mob that exists in the level somewhere
// THIS CLASS IS A STUB
public class MobInstance extends AbstractEntity {
  private int mobPrototypeID;

  public MobInstance(int x, int y, MobPrototype mobPrototype) {
    super(x, y);
    this.mobPrototypeID = mobPrototype.getID();
    this.health = mobPrototype.getMaxHealth();
  }

  @Override
  public void draw() {
    // TODO
  }
}
