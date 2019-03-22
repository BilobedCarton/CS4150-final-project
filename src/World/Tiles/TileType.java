package World.Tiles;

import World.Effects.AbstractEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TileType {
  private static int ID_COUNTER = 0;
  public static ArrayList<TileType> _tileTypes = new ArrayList<>();

  public final int ID;
  private String name;
  private List<AbstractEffect> effects;
  private Color color;

  public TileType(String name, List<AbstractEffect> effects, Color color) {
    this.ID = ID_COUNTER++;
    this.name = name;
    this.effects = effects;
    this.color = color;

    _tileTypes.add(this);
  }

  public String getName() {
    return name;
  }

  public List<AbstractEffect> getEffects() {
    return effects;
  }

  public Color getColor() {
    return color;
  }
}