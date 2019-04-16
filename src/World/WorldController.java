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
import World.Entities.Projectile;
import World.Tiles.*;
import World.WorldGeneration.LevelBuilder;
import processing.core.PApplet;

public class WorldController {
  public boolean DEBUG_MODE = false;

  // The current instance of the WorldController
  public static WorldController _instance;

  // Random object used across the program
  public Random rand;
  // Reference to sketch so we can do certain draw actions here.
  public PApplet sketch;

  public boolean resetMap;

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
  private List<Projectile> projectiles;
  private int score;
  public Player player;

  public WorldController(int mapWidth, int mapHeight, int cellDimension, Random rand, PApplet sketch) {
    this.mapWidth = mapWidth;
    this.mapHeight = mapHeight;
    this.cellDimension = cellDimension;
    this.sketch = sketch;
    this.tiles = new int[mapWidth][mapHeight];
    this.rand = rand;
    this.score = 0;

    // Instantiate the global instance of the controller to this one.
    WorldController._instance = this;
  }

  public static void reset() {
    CollectiblePrototype.reset();
    TileType.reset();
    WorldController._instance.setup();
  }

  private void setup() {
    AbstractEntity.resetIDCounter();
    this.resetMap = false;
    this.collectiblePrototypes = new ArrayList<>();
    this.tileTypes = new ArrayList<>();
    this.mobs = new ArrayList<>();
    this.collectibles = new ArrayList<>();
    this.projectiles = new ArrayList<>();

    // begin world generation:
    LevelBuilder levelBuilder = new LevelBuilder(this.rand);
    this.generateTileTypes();
    this.generateCollectiblePrototypes();
    levelBuilder.buildLevel(this.tileTypes, this.collectiblePrototypes);
    this.tiles = levelBuilder.getMap();
    this.player = new Player(mapWidth / 2, mapHeight / 2);
  }

  public void executeTick() {
    ArrayList<AbstractMob> mobsToBeDeleted = new ArrayList<>();
    ArrayList<Projectile> projectilesToBeDeleted = new ArrayList<>();
    for(AbstractMob mob : mobs) {
      mob.executeBehavior();
      if(mob.isAlive() == false) {
        mobsToBeDeleted.add(mob);
      }
    }
    for(AbstractMob mob : mobsToBeDeleted) {
      mobs.remove(mob);
    }
    checkCollectibles();
    applyTerrainEffects();
    for(Projectile p : projectiles) {
      p.update();
    }
    for(Projectile p : projectiles) {
      if(p.hitSomething()) {
        projectilesToBeDeleted.add(p);
      }
    }
    for(Projectile pToBeDeleted : projectilesToBeDeleted) {
      projectiles.remove(pToBeDeleted);
    }
    if(this.resetMap) {
      WorldController.reset();
    }
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
    for(AbstractMob mob : mobs) {
      mob.draw();
    }
    player.draw();
    for (Projectile p: projectiles) {
      p.draw();
    }
  }

  public void incrementScore() {
    this.score++;
    System.out.println("Score: " + score);
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

  public Point getPointAtPixelCoordinates(int x, int y) {
    return new Point((int)Math.floor((float)x / (float) cellDimension), (int)Math.floor((float)y / (float) cellDimension));
  }

  public void addMob(AbstractMob mob) {
    this.mobs.add(mob);
  }

  public void addProjectile(Projectile p) {
    this.projectiles.add(p);
  }

  // Generates the TileType objects we use to represent types of terrain
  private void generateTileTypes() {
    this.tileTypes.add(new TileType("Wall", new ArrayList<>(), Color.black, 0));
    this.tileTypes.add(new TileType("Dirt", new ArrayList<>(), Color.gray, 0.46));
    this.tileTypes.add(new TileType("Water", Arrays.asList(new AbstractEffect[]{new HealEffect(1)}), Color.blue, 0.27));
    this.tileTypes.add(new TileType("Lava", Arrays.asList(new AbstractEffect[]{new HurtEffect(2)}), Color.RED.darker(), 0.27));
  }

  private void generateCollectiblePrototypes() {
    this.collectiblePrototypes.add(new Treasure());
    this.collectiblePrototypes.add(new HealthPotion());
  }

  private void checkCollectibles() {
    ArrayList<CollectibleInstance> collected = new ArrayList<>();
    for(CollectibleInstance ci : collectibles) {
      if(player.getX() == ci.getX() && player.getY() == ci.getY()) {
        ci.collect();
        collected.add(ci);
      }
    }
    for(CollectibleInstance collectedInstance : collected) {
      collectibles.remove(collectedInstance);
    }
  }

  private void applyTerrainEffects() {
    for(AbstractEntity entity : mobs) {
      TileType tileOn = this.getTileTypeOfGivenTile(entity.getX(), entity.getY());
      for (AbstractEffect effect : tileOn.getEffects()) {
        effect.applyEffect(entity.getX(), entity.getY());
      }
    }
    TileType tileOn = this.getTileTypeOfGivenTile(player.getX(), player.getY());
    for (AbstractEffect effect : tileOn.getEffects()) {
      effect.applyEffect(player.getX(), player.getY());
    }
  }
}