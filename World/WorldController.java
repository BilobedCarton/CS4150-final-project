public class WorldController {

  // The current instance of the WorldController
  public static WorldController _instance;

  // 2D list of tile IDs designating the tile type of each pair of coordinates
  private int[][] tiles;
  private List<AbstractObstacle> obstacles;
  private List<AbstractMob> mobs;

  public WorldController(int mapWidth, int mapHeight) {
    this.tiles = new int[mapWidth][mapHeight];
    this.obstacles = new ArrayList<AbstractObstacle>();
    this.mobs = new ArrayList<AbstractMob>();
  }
}