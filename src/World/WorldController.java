package World;

import java.awt.*;
import java.util.*;
import java.util.List;

import World.Collectibles.CollectibleInstance;
import World.Collectibles.CollectiblePrototype;
import World.Collectibles.HealthPotion;
import World.Collectibles.Treasure;
import World.Effects.*;
import World.Entities.*;
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

  // This is in number of terrain cells (not pixels)
  public int mapWidth;
  public int mapHeight;
  public int cellDimension;

  public List<CollectiblePrototype> collectiblePrototypes;
  public List<TileType> tileTypes;

  // 2D list of tile IDs designating the tile type of each pair of coordinates
  private int[][] tiles;
  private List<AbstractMob> mobs;
  private List<CollectibleInstance> collectibles;
  public Player player;

  public WorldController(int mapWidth, int mapHeight, int cellDimension, Random rand, PApplet sketch) {
    this.mapWidth = mapWidth;
    this.mapHeight = mapHeight;
    this.cellDimension = cellDimension;
    this.sketch = sketch;
    this.tiles = new int[mapWidth][mapHeight];
    this.rand = rand;
    this.collectiblePrototypes = new ArrayList<>();
    this.tileTypes = new ArrayList<>();
    this.mobs = new ArrayList<>();
    this.collectibles = new ArrayList<>();
    this.player = new Player(mapWidth / 2 * cellDimension, mapHeight / 2  * cellDimension);

    // Instantiate the global instance of the controller to this one.
    WorldController._instance = this;
  }

  public static void reset() {
    CollectiblePrototype.reset();
    TileType.reset();
    WorldController._instance.setup();
  }

  private void setup() {
    // begin world generation:
    LevelBuilder levelBuilder = new LevelBuilder(this.rand);
    this.generateTileTypes();
    this.generateCollectiblePrototypes();
    levelBuilder.buildLevel(this.tileTypes, this.collectiblePrototypes);
    this.tiles = levelBuilder.getMap();
  }

  public void draw() {
    for(int x = 0; x < this.mapWidth; x++) {
      for(int y = 0; y < this.mapHeight; y++) {
        if(tiles[x][y] != 0) {
          Color c = tileTypes.get(tiles[x][y]).getColor();
          sketch.fill(c.getRed(), c.getGreen(), c.getBlue());
          sketch.stroke(c.getRed(), c.getGreen(), c.getBlue());
        } else {
          sketch.fill(0);
          sketch.stroke(0);
        }
        sketch.rect(
                x * cellDimension,
                y * cellDimension,
                (x + 1) * cellDimension,
                (y + 1) * cellDimension);
      }
    }
    for(CollectibleInstance collectibleInstance : collectibles) {
      collectibleInstance.draw();
    }
    player.draw();
  }

  public void placeCollectible(CollectibleInstance collectibleInstance) {
    this.collectibles.add(collectibleInstance);
  }

  public List<AbstractEntity> getEntitiesOnTile(int x, int y) {
    List<AbstractEntity> entities = new ArrayList<>();
    for(AbstractMob mob : this.mobs) {
      if(mob.getX() == x && mob.getY() == y) {
        entities.add(mob);
      }
    }
    if(player.getX() == x && player.getY() == y) {
      entities.add(player);
    }
    return entities;
  }

  public TileType getTileTypeOfGivenTile(int x, int y) {
    return tileTypes.get(tiles[x][y]);
  }

  // Generates the TileType objects we use to represent types of terrain
  private void generateTileTypes() {
    this.tileTypes.add(new TileType("Wall", null, Color.black, 0));
    this.tileTypes.add(new TileType("Dirt", Arrays.asList(new AbstractEffect[]{new ResetEffect()}), Color.gray, 0.35));
    this.tileTypes.add(new TileType("Water", Arrays.asList(new AbstractEffect[]{new HealEffect(1), new ResetEffect()}), Color.blue, 0.20));
    this.tileTypes.add(new TileType("Lava", Arrays.asList(new AbstractEffect[]{new HurtEffect(2), new ResetEffect()}), Color.RED.darker(), 0.22));
    this.tileTypes.add(new TileType("Mud", Arrays.asList(new AbstractEffect[]{new SlowEffect(0.75)}), new Color(150, 75, 0).darker(), 0.23));
  }

  private void generateCollectiblePrototypes() {
    this.collectiblePrototypes.add(new Treasure());
    this.collectiblePrototypes.add(new HealthPotion());
  }
}