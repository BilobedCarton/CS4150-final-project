package World.WorldGeneration;

import java.util.List;
import java.util.Random;

import World.Tiles.TileType;
import World.WorldController;

public class LevelBuilder {
  private final static int SMOOTH_PASSES = 5;

  private Random rand;
  private int width;
  private int height;
  public int[][] map;

  public LevelBuilder(Random rand) {
    this.rand = rand;
    this.width = WorldController._instance.mapWidth;
    this.height = WorldController._instance.mapHeight;
    this.map = new int[this.width][this.height];
  }

  public int[][] buildLevel(List<TileType> tileTypes) {
    this.buildSimpleMap(rand.nextInt(5) + 42);
    for(int i = 0; i < SMOOTH_PASSES; i++) {
      this.smoothMapWalls();
    }
    this.assignTileTypes(tileTypes);
    for(int j = 0; j < SMOOTH_PASSES / 2; j++) {
      this.smoothMapTiles(tileTypes);
    }
    return map;
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
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        if(map[i][j] != 0) {
          int[] tileProbabilities = new int[tileTypes.size()];
          for(int k = 0; k < tileTypes.size(); k++) {
            tileProbabilities[k] = tileTypes.get(k).getWeightedChance();
          }
          if (i > 0 && j > 0) {
            if(map[i - 1][j] >= 0) {
              tileProbabilities[map[i - 1][j]] += 3 * tileTypes.get(map[i - 1][j]).getWeightedChance();
            }
            if(map[i - 1][j - 1] >= 0) {
              tileProbabilities[map[i - 1][j - 1]] += 3 * tileTypes.get(map[i - 1][j - 1]).getWeightedChance();
            }
            if(map[i][j - 1] >= 0) {
              tileProbabilities[map[i][j - 1]] += 3 * tileTypes.get(map[i][j - 1]).getWeightedChance();
            }
            if(map[i + 1][j - 1] >= 0) {
              tileProbabilities[map[i][j - 1]] += 3 * tileTypes.get(map[i][j - 1]).getWeightedChance();
            }
          }
          tileProbabilities[0] = 0;
          int choice = rand.nextInt(sum(tileProbabilities));
          for (int k = 0; k < tileTypes.size(); k++) {
            choice -= tileProbabilities[k];
            if (choice <= 0) {
              map[i][j] = k;
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
  private int sum (int[] arr) {
    int sum = 0;
    for(int i : arr) {
      sum += i;
    }
    return sum;
  }
}
