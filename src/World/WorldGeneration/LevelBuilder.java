package World.WorldGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import World.Collectibles.CollectiblePrototype;
import World.Tiles.TileType;
import World.WorldController;
import processing.core.PVector;

public class LevelBuilder {
  private final static int SMOOTH_PASSES = 5;
  private final static int DIST_FROM_WALL = 3;

  private Random rand;
  private int width;
  private int height;
  private int[][] map;

  public LevelBuilder(Random rand) {
    this.rand = rand;
    this.width = WorldController._instance.mapWidth;
    this.height = WorldController._instance.mapHeight;
    this.map = new int[this.width][this.height];
  }

  public int[][] getMap() {
    return map;
  }

  // map creation based upon techniques applied in the following video by Sebastian Lague:
  // https://www.youtube.com/watch?v=v7yyZZjF1z4&list=PLFt_AvWsXl0eZgMK_DT5_biRkWXftAOf9
  public void buildLevel(List<TileType> tileTypes, List<CollectiblePrototype> collectiblePrototypes) {
    this.buildSimpleMap(rand.nextInt(5) + 42);
    for(int i = 0; i < SMOOTH_PASSES; i++) {
      this.smoothMapWalls();
    }
    this.assignTileTypes(tileTypes);
    for(int j = 0; j < SMOOTH_PASSES; j++) {
      this.smoothMapTiles(tileTypes);
    }
    this.placeCollectables(collectiblePrototypes);
  }

  public void placeCollectables(List<CollectiblePrototype> collectiblePrototypes) {
    List<PVector> cornerPoints = this.findOpenCorners();
    boolean placedTreasure = false;
    for(int i = 0; i < cornerPoints.size(); i++) {
      if((i == cornerPoints.size() - 1 || rand.nextInt(2) == 1) && placedTreasure == false) {
        collectiblePrototypes.get(0).placeInstance((int) cornerPoints.get(i).x, (int) cornerPoints.get(i).y);
        placedTreasure = true;
      }
      else{
        int k = rand.nextInt(collectiblePrototypes.size() - 1) + 1;
        collectiblePrototypes.get(k).placeInstance((int) cornerPoints.get(i).x, (int) cornerPoints.get(i).y);
      }
    }
  }

  private List<PVector> findOpenCorners() {
    // first we find the corners of our level.
    List<PVector> openCornerPoints = new ArrayList<>();
    // top left
    int distFromWall = 0;
    for(int x = 0, y = 0; x < width / 2 && y < height / 2; x++, y++) {
      if(map[x][y] != 0) {
        if(distFromWall >= DIST_FROM_WALL) {
          openCornerPoints.add(new PVector(x, y));
          break;
        }
        distFromWall++;
      }
      else {
        distFromWall = 0;
      }
    }
    // top right
    for(int x = width - 1, y = 0; x > width / 2 && y < height / 2; x--, y++) {
      if(map[x][y] != 0) {
        if(distFromWall >= DIST_FROM_WALL) {
          openCornerPoints.add(new PVector(x, y));
          break;
        }
        distFromWall++;
      }
      else {
        distFromWall = 0;
      }
    }
    // bottom right
    for(int x = width - 1, y = height - 1; x > width / 2 && y > height / 2; x--, y--) {
      if(map[x][y] != 0) {
        if(distFromWall >= DIST_FROM_WALL) {
          openCornerPoints.add(new PVector(x, y));
          break;
        }
        distFromWall++;
      }
      else {
        distFromWall = 0;
      }
    }
    // bottom left
    for(int x = 0, y = height - 1; x < width / 2 && y > height / 2; x++, y--) {
      if(map[x][y] != 0) {
        if(distFromWall >= DIST_FROM_WALL) {
          openCornerPoints.add(new PVector(x, y));
          break;
        }
        distFromWall++;
      }
      else {
        distFromWall = 0;
      }
    }
    return openCornerPoints;
  }

  private void buildSimpleMap(int fillPercent) {
    fillPercent = Math.abs(fillPercent) > 100 ? 100 : Math.abs(fillPercent);
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        if(x == 0 || x == width - 1 || y == 0 || y == height - 1) {
          map[x][y] = 0;
        }
        else {
          map[x][y] = rand.nextInt(100) < fillPercent ? 0 : 1;
        }
      }
    }
  }

  private void smoothMapWalls() {
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        int surroundingWallCount = getSurroundingWallCount(x, y);
        if(surroundingWallCount > 4) {
          map[x][y] = 0;
        }
        else if (surroundingWallCount < 4) {
          map[x][y] = 1;
        }
      }
    }
  }

  int getSurroundingWallCount(int x, int y) {
    int count = 0;
    for(int neighborX = x - 1; neighborX <= x + 1; neighborX++) {
      for(int neighborY = y - 1; neighborY <= y + 1; neighborY++) {
        if(neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
          if (neighborX != x || neighborY != y) {
            count += map[neighborX][neighborY];
          }
        }
      }
    }
    return 8 - count;
  }

  private void assignTileTypes(List<TileType> tileTypes) {
    double[][][] tileTypeProbabilities = new double[width][height][tileTypes.size()];
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        for(int k = 0; k < tileTypes.size(); k++) {
          tileTypeProbabilities[x][y][k] = tileTypes.get(k).getWeightedChance();
        }
      }
    }
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        if(map[x][y] > 0) {
          tileTypeProbabilities[x][y][0] = 0;
          tileTypeProbabilities[x][y] = normalize(tileTypeProbabilities[x][y].clone());
          float choice = rand.nextFloat();
          for (int k = 1; k < tileTypes.size(); k++) {
            choice -= tileTypeProbabilities[x][y][k];
            if (choice <= 0) {
              map[x][y] = k;
              break;
            }
          }
        }
      }
    }
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        for(int k = 1; k < tileTypes.size(); k++) {
          tileTypeProbabilities[x][y][k] += 3 * getSurroundingTileCount(k, x, y) * tileTypes.get(k).getWeightedChance();
        }
        if(map[x][y] > 0) {
          tileTypeProbabilities[x][y][0] = 0;
          tileTypeProbabilities[x][y] = normalize(tileTypeProbabilities[x][y].clone());
          float choice = rand.nextFloat();
          for (int k = 1; k < tileTypes.size(); k++) {
            choice -= tileTypeProbabilities[x][y][k];
            if (choice <= 0) {
              map[x][y] = k;
              break;
            }
          }
        }
      }
    }
  }

  private void smoothMapTiles(List<TileType> tileTypes) {
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        if(map[x][y] != 0) {
          for (int i = 1; i < tileTypes.size(); i++) {
            if (getSurroundingTileCount(i, x, y) > 4) {
              map[x][y] = i;
            }
          }
        }
      }
    }
  }

  private int getSurroundingTileCount(int tileType, int x, int y) {
    int count = 0;
    for(int neighborX = x - 1; neighborX <= x + 1; neighborX++) {
      for(int neighborY = y - 1; neighborY <= y + 1; neighborY++) {
        if(neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
          if ((neighborX != x || neighborY != y) && map[neighborX][neighborY] == tileType) {
            count++;
          }
        }
      }
    }
    return count;
  }

  // helper function to sum an array of ints
  private double[] normalize(double[] arr) {
    double sum = 0;
    for(double a : arr) {
      sum += a;
    }
    for(int i = 0; i < arr.length; i++) {
      arr[i] /= sum;
    }
    return arr;
  }
}
