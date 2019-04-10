package World.Entities.Utilities;

import World.Entities.AbstractEntity;
import World.WorldController;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AStarPath {
    private class HashMapWithDefaultValue<K, V> extends HashMap<K, V> {
        protected V defaultValue;
        public HashMapWithDefaultValue(V defaultValue) {
            this.defaultValue = defaultValue;
        }

        @Override
        public V get(Object k) {
            return containsKey(k) ? super.get(k) : defaultValue;
        }
    }

    public Point[] path;
    public boolean succeeded;

    public AStarPath(AbstractEntity a, AbstractEntity b) {
        this.succeeded = aStar(new Point(a.getX(), a.getY()), new Point(b.getX(), b.getY()));
    }

    public AStarPath(Point a, Point b) {
        this.succeeded = aStar(a, b);
    }

    private void reconstructPath(HashMap<Point, Point> cameFrom, Point current) {
        ArrayList<Point> path = new ArrayList<>();
        path.add(current);
        while(cameFrom.keySet().contains(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        this.path =(Point[]) path.toArray();
    }

    private boolean aStar(Point start, Point goal) {
        ArrayList<Point> closedSet = new ArrayList<>();
        ArrayList<Point> openSet = new ArrayList<>();
        openSet.add(start);
        HashMap<Point, Point> cameFrom = new HashMap<>();
        HashMap<Point, Integer> gScore = new HashMapWithDefaultValue<>(Integer.MAX_VALUE);
        gScore.put(start, 0);
        HashMap<Point, Integer> fScore = new HashMapWithDefaultValue<>(Integer.MAX_VALUE);
        fScore.put(start, heuristicCostEstimate(start, goal));

        while(openSet.size() > 0) {
            Point current = getPointWithLowestFScore(fScore);
            if(current.equals(goal)) {
                reconstructPath(cameFrom, current);
                return true;
            }

            openSet.remove(current);
            closedSet.add(current);

            for(int i = 0; i < 4; i++) {
                Point neighbor;
                if(i == 0) {
                    neighbor = new Point(current.x - 1, current.y);
                }
                else if(i == 1) {
                    neighbor = new Point(current.x, current.y - 1);
                }
                else if(i == 2) {
                    neighbor = new Point(current.x + 1, current.y);
                }
                else {
                    neighbor = new Point(current.x, current.y + 1);
                }
                // Skip walls
                if(WorldController._instance.getTileTypeOfGivenTile(neighbor.x, neighbor.y).ID == 0) {
                    continue;
                }
                if(closedSet.contains(neighbor)) {
                    continue;
                }
                int tentativeGScore = gScore.get(current) + 1;
                if(openSet.contains(neighbor) == false) {
                    openSet.add(neighbor);
                }
                else if (tentativeGScore >= gScore.get(neighbor)) {
                    continue;
                }
                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentativeGScore);
                fScore.put(neighbor, gScore.get(neighbor) + heuristicCostEstimate(neighbor, goal));
            }
        }
        // Failed to construct path
        return false;
    }

    private int heuristicCostEstimate(Point start, Point goal) {
        return Math.abs(goal.x - start.x) + Math.abs(goal.y - start.y);
    }

    private Point getPointWithLowestFScore(HashMap<Point, Integer> fScore) {
        if(fScore.keySet().size() == 0) {
            return null;
        }
        Point currLowest = (Point) fScore.keySet().toArray()[0];
        for(Point p : fScore.keySet()) {
           if(fScore.get(p) < fScore.get(currLowest)) {
               currLowest = p;
           }
        }
        return currLowest;
    }
}
