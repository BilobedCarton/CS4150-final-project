package World;

import java.awt.*;
import java.util.*;
import java.util.List;

import World.Obstacles.*;
import World.Mobs.*;
import World.Tiles.*;
import World.WorldGeneration.LevelBuilder;
import processing.core.PApplet;

public class WorldController {

  // The current instance of the WorldController
  public static WorldController _instance;

  // Random object used across the program
  public Random rand;
  // Reference to sketch so we can do certain draw actions here.
  public PApplet sketch;

  // 2D list of tile IDs designating the tile type of each pair of coordinates
  private int mapWidth;
  private int mapHeight;
  private int[][] tiles;
  private List<AbstractObstacle> obstacles;
  private List<MobPrototype> mobPrototypes;
  private List<TileType> tileTypes;

  public WorldController(int mapWidth, int mapHeight, String seed, PApplet sketch) {
    this.mapWidth = mapWidth;
    this.mapHeight = mapHeight;
    this.sketch = sketch;
    this.tiles = new int[mapWidth][mapHeight];
    this.rand = new Random(seed.hashCode());
    this.obstacles = new ArrayList<>();
    this.mobPrototypes = new ArrayList<>();
    this.tileTypes = new ArrayList<>();

    // Instantiate the global instance of the controller to this one.
    WorldController._instance = this;

    // begin world generation:
    LevelBuilder levelBuilder = new LevelBuilder(this.rand);
    this.generateTileTypes();
    this.tiles = levelBuilder.buildLevel(this.tileTypes).clone();
  }

  public void draw() {
    for(int x = 0; x < this.mapWidth; x++) {
      for(int y = 0; y < this.mapHeight; y++) {
        if(tiles[x][y] >= 0) {
          Color c = tileTypes.get(tiles[x][y]).getColor();
          sketch.fill(c.getRed(), c.getGreen(), c.getBlue());
          sketch.stroke(c.getRed(), c.getGreen(), c.getBlue());
        } else {
          sketch.fill(0);
          sketch.stroke(0);
        }
        sketch.rect(x * 20, y * 20, (x + 1) * 20, (y + 1) * 20);
      }
    }
  }

  // Generates the TileType objects we use to represent types of terrain
  private void generateTileTypes() {
    // Stub
    this.tileTypes.add(new TileType("Floor", null, Color.white));
  }
}