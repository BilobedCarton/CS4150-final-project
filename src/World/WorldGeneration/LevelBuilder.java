package World.WorldGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import World.Tiles.TileType;
import World.WorldController;

public class LevelBuilder {
  private static int levelWidth = 3;
  private static int levelHeight = 3;
  private static int numTemplatesPerType = 1;

  RoomTemplate[][] roomTemplates;
  Random rand;

  public LevelBuilder(Random rand) {
    this.rand = rand;
    this.parseRoomTemplates();
  }

  public int[][] buildLevel(List<TileType> tileTypes) {
    boolean[][] floorMap = this.getFloorMap();
    return this.assignTileTypes(floorMap, tileTypes);
  }

  private void parseRoomTemplates() {
    roomTemplates = new RoomTemplate[3][numTemplatesPerType];
    // Read a text file containing data representing room templates and then create representations and add them to our list

    // TEMP
    boolean[][] centerLayout = new boolean[10][10];
    boolean[][] cornerLayout = new boolean[10][10];
    boolean[][] edgeLayout = new boolean[10][10];
    for(int i = 0; i < 10; i++) {
      for(int j = 0 ; j < 10; j++) {
        centerLayout[i][j] = true;
        cornerLayout[i][j] = true;
        edgeLayout[i][j] = true;
        if((i == 5 || i == 6) && (j == 5 || j == 6)) {
          centerLayout[i][j] = false;
        }
        if(i == 0 || j == 0) {
          cornerLayout[i][j] = false;
        }
        if(i == 0) {
          edgeLayout[i][j] = false;
        }
      }
    }
    roomTemplates[RoomTemplate.RoomType.CENTER.getValue()][0] = new RoomTemplate(RoomTemplate.RoomType.CENTER, centerLayout);
    roomTemplates[RoomTemplate.RoomType.CORNER.getValue()][0] = new RoomTemplate(RoomTemplate.RoomType.CORNER, cornerLayout);
    roomTemplates[RoomTemplate.RoomType.EDGE.getValue()][0] = new RoomTemplate(RoomTemplate.RoomType.EDGE, edgeLayout);
  }

  private boolean[][] getFloorMap() {
    boolean[][] map = new boolean[levelWidth * 10][levelHeight * 10];
    for(int i = 0; i < levelWidth; i++) {
      for(int j = 0; j < levelHeight; j++) {
        RoomTemplate.RoomType type;
        if(i != 0 && i != levelWidth - 1 && j != 0 && j != levelHeight - 1) {
          // CENTER
          type = RoomTemplate.RoomType.CENTER;
        }
        else if((i == 0 || i == levelWidth - 1) && (j == 0 || j == levelHeight - 1)) {
          // CORNER
          type = RoomTemplate.RoomType.CORNER;
        }
        else {
          // EDGE
          type = RoomTemplate.RoomType.EDGE;
        }
        boolean[][] floorSection = rotateRoomTemplate(
                roomTemplates[type.getValue()][rand.nextInt(numTemplatesPerType)],
                rand.nextInt(4));
        for(int x = 0; x < 10; x++) {
          for(int y = 0; y < 10; y++) {
            map[x + (i * 10)][y + (j * 10)] = floorSection[x][y];
          }
        }
      }
    }
    return map;
  }

  private int[][] assignTileTypes(boolean[][] floorMap, List<TileType> tileTypes) {
    int[][] tileMap = new int[levelWidth * 10][levelHeight * 10];
    for(int i = 0; i < levelWidth * 10; i++) {
      for(int j = 0; j < levelHeight * 10; j++) {
        if(floorMap[i][j] != true) {
          tileMap[i][j] = -1;
        }
        else {
          int[] tileProbabilities = new int[tileTypes.size()];
          for (int k = 0; k < tileTypes.size(); k++) {
            tileProbabilities[k] = 10;
          }
          int extraMods = 0;
          if (i > 0 && j > 0) {
            if(tileMap[i - 1][j] >= 0) {
              tileProbabilities[tileMap[i - 1][j]] += 5;
              extraMods += 5;
            }
            if(tileMap[i - 1][j - 1] >= 0) {
              tileProbabilities[tileMap[i - 1][j - 1]] += 5;
              extraMods += 5;
            }
            if(tileMap[i][j - 1] >= 0) {
              tileProbabilities[tileMap[i][j - 1]] += 5;
              extraMods += 5;
            }
          }
          int choice = rand.nextInt(extraMods + (10 * (tileTypes.size())));
          for (int k = 0; k < tileTypes.size(); k++) {
            choice -= tileProbabilities[k];
            if (choice <= 0) {
              tileMap[i][j] = k;
            }
          }
        }
      }
    }
    return tileMap;
  }

  // Orientation: 0 - no rotation, 1 - counterclockwise 90 degrees, 2 - reversed, 3 - clockwise 90 degrees
  private boolean[][] rotateRoomTemplate(RoomTemplate template, int orientation) {
    orientation = orientation % 4;
    boolean[][] templateLayout = template.floorSpace.clone();
    for(int i = 0; i < orientation; i++) {
      rotateMatrix(10, templateLayout);
    }
    return templateLayout;
  }

  // Source: https://www.geeksforgeeks.org/inplace-rotate-square-matrix-by-90-degrees/
  // An Inplace function to rotate a N x N matrix
  // by 90 degrees in anti-clockwise direction
  static boolean[][] rotateMatrix(int N, boolean mat[][])
  {
    // Consider all squares one by one
    for (int x = 0; x < N / 2; x++)
    {
      // Consider elements in group of 4 in
      // current square
      for (int y = x; y < N-x-1; y++)
      {
        // store current cell in temp variable
        boolean temp = mat[x][y];

        // move values from right to top
        mat[x][y] = mat[y][N-1-x];

        // move values from bottom to right
        mat[y][N-1-x] = mat[N-1-x][N-1-y];

        // move values from left to bottom
        mat[N-1-x][N-1-y] = mat[N-1-y][x];

        // assign temp to left
        mat[N-1-y][x] = temp;
      }
    }
    return mat;
  }
}
