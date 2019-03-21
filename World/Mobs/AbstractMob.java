public abstract class AbstractMob {
  private constant int ID_COUNTER = 0;

  private final int ID;

  public AbstractMob() {
    this.ID = ID_COUNTER++;
  }
}