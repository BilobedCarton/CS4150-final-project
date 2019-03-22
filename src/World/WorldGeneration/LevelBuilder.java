package World.WorldGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import World.Tiles.TileType;
import World.WorldController;

public class LevelBuilder {
  private static int templateWidth = 5;
  private static int templateHeight = 5;
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
    boolean[][] centerLayout = new boolean[templateWidth][templateHeight];
    boolean[][] cornerLayout = new boolean[templateWidth][templateHeight];
    boolean[][] edgeLayout = new boolean[templateWidth][templateHeight];
    for(int i = 0; i < templateWidth; i++) {
      for(int j = 0 ; j < templateHeight; j++) {
        centerLayout[i][j] = true;
        cornerLayout[i][j] = true;
        edgeLayout[i][j] = true;
        if((i == templateWidth / 2 || i == templateWidth / 2 + 1)
                && (j == templateHeight / 2 || j == templateHeight / 2 + 1)) {
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
    boolean[][] map = new boolean[WorldController._instance.mapWidth][WorldController._instance.mapHeight];
    for(int i = 0; i < WorldController._instance.mapWidth / templateWidth; i++) {
      for(int j = 0; j < WorldController._instance.mapHeight / templateHeight; j++) {
        RoomTemplate.RoomType type;
        if(i != 0 && i != WorldController._instance.mapWidth / templateWidth - 1
                && j != 0 && j != WorldController._instance.mapHeight / templateHeight - 1) {
          // CENTER
          type = RoomTemplate.RoomType.CENTER;
        }
        else if((i == 0 || i == WorldController._instance.mapWidth / templateWidth - 1)
                && (j == 0 || j == WorldController._instance.mapHeight / templateHeight - 1)) {
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
        for(int x = 0; x < templateWidth; x++) {
          for(int y = 0; y < templateHeight; y++) {
            map[x + (i * templateWidth)][y + (j * templateHeight)] = floorSection[x][y];
          }
        }
      }
    }
    return map;
  }

  private int[][] assignTileTypes(boolean[][] floorMap, List<TileType> tileTypes) {
    int[][] tileMap = new int[WorldController._instance.mapWidth][WorldController._instance.mapHeight];
    for(int i = 0; i < WorldController._instance.mapWidth; i++) {
      for(int j = 0; j < WorldController._instance.mapHeight; j++) {
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
      rotateMatrix(templateWidth, templateLayout);
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
