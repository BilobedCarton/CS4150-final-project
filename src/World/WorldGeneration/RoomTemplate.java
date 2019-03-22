package World.WorldGeneration;

public class RoomTemplate {
  public enum RoomType {
    CORNER (0),
    EDGE (1),
    CENTER (2);

    private int _value;

    RoomType(int value) {
      this._value = value;
    }

    public int getValue() {
      return _value;
    }

    public static RoomType fromInt(int i) {
      for (RoomType r : RoomType.values()) {
        if (r.getValue() == i) {
          return r;
        }
      }
      return null;
    }
  }

  public RoomType type;
  public boolean[][] floorSpace;

  public RoomTemplate(RoomType type, boolean[][] floorSpace) {
    this.type = type;
    this.floorSpace = floorSpace;
  }
}
