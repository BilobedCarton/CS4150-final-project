package World.Mobs;

// We use these prototypes to create mobs of this type.
// See Mob class for more info on process.
public class MobPrototype {
  private static int ID_COUNTER = 0;

  public final int ID;

  public MobPrototype() {
    this.ID = ID_COUNTER++;
  }
}