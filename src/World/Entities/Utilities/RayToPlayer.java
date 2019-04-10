package World.Entities.Utilities;

import World.Tiles.TileType;
import World.WorldController;

import java.awt.*;
import java.util.ArrayList;

public class RayToPlayer {
    public Point[] ray;

    public RayToPlayer(Point origin) {
        Point b = new Point(WorldController._instance.player.getX(), WorldController._instance.player.getY());
        ray = (Point[]) bresenhamLine(origin, b).toArray();
    }

    public boolean canSeePlayerFromPoint() {
        for(Point pt : ray) {
            if(canSeeThroughPoint(pt.x, pt.y) == false) {
                return false;
            }
        }
        return true;
    }

    // x and y are pixel points
    private boolean canSeeThroughPoint(int x, int y) {
        int tileX = (int)Math.floor((float)x / (float) WorldController._instance.cellDimension);
        int tileY = (int)Math.floor((float)y / (float)WorldController._instance.cellDimension);

        if(tileX < 0) { return false; }
        if(tileX >= WorldController._instance.mapWidth) { return false; }
        if(tileY < 0) { return false; }
        if(tileY >= WorldController._instance.mapHeight) { return false; }

        TileType tileType = WorldController._instance.getTileTypeOfGivenTile(tileX, tileY);
        if(tileType.ID == 0) {
            return false;
        }
        return true;
    }

    // Raycast using the Bresenham Algorithm
    private ArrayList<Point> bresenhamLine(Point a, Point b) {
        ArrayList<Point> line = new ArrayList<>();
        boolean steep = Math.abs(b.y - a.y) > Math.abs(b.x - a.x);
        if(steep) {
            int temp = a.x;
            a.x = a.y;
            a.y = temp;
            temp = b.x;
            b.x = b.y;
            b.y = temp;
        }
        if(a.x > b.x) {
            int temp = a.x;
            a.x = b.x;
            b.x = temp;
            temp = a.y;
            a.y = b.y;
            b.y = a.y;
        }

        int deltaX = b.x - a.x;
        int deltaY = b.y - a.y;
        int error = 0;
        int yStep;
        int y = a.y;
        if (a.y < b.y) {
            yStep = 1;
        }
        else {
            yStep = -1;
        }
        for(int x = a.x; x < b.x; x++) {
            if(steep) { line.add(new Point(y, x)); }
            else { line.add(new Point(x, y)); }
            error += deltaY;
            if (2 * error >= deltaX) {
                y += yStep;
                error -= deltaX;
            }
        }
        return line;
    }
}
