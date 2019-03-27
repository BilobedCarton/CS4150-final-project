package World.Entities;

// We use these prototypes to create mobs of this type.
// See MobInstance class for more info on process.

// THIS CLASS IS A STUB
public class MobPrototype {
  private static int ID_COUNTER = 0;

  private final int ID;
  private int maxHealth;

  public MobPrototype(int health) {
    this.ID = ID_COUNTER;
    ID_COUNTER++;
    this.maxHealth = health;
  }

  public static void reset() {
    ID_COUNTER = 0;
  }

  public int getID() {
    return ID;
  }

  public int getMaxHealth() {
    return maxHealth;
  }
}