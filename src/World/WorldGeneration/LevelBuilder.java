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
    this.buildSimpleMap(45);
    for(int i = 0; i < SMOOTH_PASSES; i++) {
      this.smoothMap();
    }
    this.assignTileTypes(tileTypes);
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

  private void smoothMap() {
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        int surroundingWallCount = getSurroundingWallCount(x, y);
        if(surroundingWallCount > 4) {
          map[x][y] = 0;
        }
        else if (surroundingWallCount < 4){
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
    int[][] tileMap = new int[WorldController._instance.mapWidth][WorldController._instance.mapHeight];
    for(int i = 0; i < WorldController._instance.mapWidth; i++) {
      for(int j = 0; j < WorldController._instance.mapHeight; j++) {
        if(1 == 0) {}
        else {
          int[] tileProbabilities = new int[tileTypes.size() - 1];
          for (int k = 0; k < tileTypes.size() - 1; k++) {
            tileProbabilities[k] = tileTypes.get(k + 1).getWeightedChance();
          }
          if (i > 0 && j > 0) {
            if(tileMap[i - 1][j] >= 0) {
              tileProbabilities[tileMap[i - 1][j] - 1] += tileTypes.get(tileMap[i - 1][j]).getWeightedChance();
            }
            if(tileMap[i - 1][j - 1] >= 0) {
              tileProbabilities[tileMap[i - 1][j - 1] - 1] += tileTypes.get(tileMap[i - 1][j - 1]).getWeightedChance();;
            }
            if(tileMap[i][j - 1] >= 0) {
              tileProbabilities[tileMap[i][j - 1] - 1] += tileTypes.get(tileMap[i][j - 1]).getWeightedChance();;
            }
          }
          int choice = rand.nextInt(sum(tileProbabilities));
          for (int k = 0; k < tileTypes.size() - 1; k++) {
            choice -= tileProbabilities[k];
            if (choice <= 0) {
              tileMap[i][j] = k + 1;
              break;
            }
          }
        }
      }
    }
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
