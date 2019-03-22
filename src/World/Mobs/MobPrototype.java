package World.Mobs;

// We use these prototypes to create mobs of this type.
// See Mob class for more info on process.

// THIS CLASS IS A STUB
public class MobPrototype {
  private static int ID_COUNTER = 0;

  public final int ID;

  private float maxHealth;

  public MobPrototype(float health) {
    this.ID = ID_COUNTER++;
    this.maxHealth = health;
  }

  public float getMaxHealth() {
    return maxHealth;
  }
}