package World.Tiles;

import World.Effects.AbstractEffect;

import java.awt.*;
import java.util.List;

public class TileType {
  private static int ID_COUNTER = 0;

  public final int ID;
  private String name;
  private List<AbstractEffect> effects;
  private Color color;
  private double weightedChance;

  public TileType(String name, List<AbstractEffect> effects, Color color, double weightedChance) {
    this.ID = ID_COUNTER;
    ID_COUNTER++;
    this.name = name;
    this.effects = effects;
    this.color = color;
    this.weightedChance = Math.abs(weightedChance) % 1.0;
  }

  public static void reset() {
    ID_COUNTER = 0;
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

  public double getWeightedChance() {
    return weightedChance;
  }
}