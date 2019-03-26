package World.WorldGeneration;

import java.util.List;
import java.util.Random;

import World.Collectibles.CollectiblePrototype;
import World.Tiles.TileType;
import World.WorldController;

public class LevelBuilder {
  private final static int SMOOTH_PASSES = 5;

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

  public void placeCollectables(List<CollectiblePrototype> collectiblePrototypes) {
    // first we find the corners of our level.

  }

  public void buildLevel(List<TileType> tileTypes) {
    this.buildSimpleMap(rand.nextInt(5) + 42);
    for(int i = 0; i < SMOOTH_PASSES; i++) {
      this.smoothMapWalls();
    }
    this.assignTileTypes(tileTypes);
    for(int j = 0; j < SMOOTH_PASSES; j++) {
      this.smoothMapTiles(tileTypes);
    }
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
