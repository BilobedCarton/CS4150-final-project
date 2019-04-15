package World.Entities.Utilities;

import World.Effects.AbstractEffect;
import World.Entities.AbstractEntity;
import World.Entities.AbstractMob;
import World.Tiles.TileType;
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

    private AbstractMob mob;

    public AStarPath(AbstractEntity a, AbstractEntity b, AbstractMob mob) {
        this(new Point(a.getX(), a.getY()), new Point(b.getX(), b.getY()), mob);
    }

    public AStarPath(Point a, Point b, AbstractMob mob) {
        this.mob = mob;
        this.succeeded = aStar(a, b);
    }

    private void reconstructPath(HashMap<Point, Point> cameFrom, Point current) {
        ArrayList<Point> path = new ArrayList<>();
        path.add(current);
        while(cameFrom.keySet().contains(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        this.path = path.toArray(new Point[path.size()]);
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
            Point current = getPointWithLowestFScore(fScore, openSet);
            if(current.equals(goal)) {
                reconstructPath(cameFrom, current);
                return true;
            }

            openSet.remove(current);
            closedSet.add(current);

            for(Point neighbor : getNeighbors(current)) {
                // Skip impassable points
                if(isImpassable(neighbor)) {
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
        // Base cost is the simple distance to goal from start.
        int cost = Math.abs(goal.x - start.x) + Math.abs(goal.y - start.y);
        // If our health would be changed by stepping onto this point we should consider this as well. Healing is good.
        cost -= getChangeInHealthFromTile(start);
        // If there is an entity in the way we should go around;
        if(WorldController._instance.getEntitiesOnTile(start.x, start.y).size() > 0 && WorldController._instance.getEntitiesOnTile(start.x, start.y).get(0).equals(this.mob) == false) {
            cost += 50;
        }
        return cost;
    }

    private Point getPointWithLowestFScore(HashMap<Point, Integer> fScore, ArrayList<Point> openSet) {
        if(openSet.size() == 0) {
            return null;
        }
        Point currLowest = openSet.get(0);
        for(Point p : openSet) {
           if(fScore.get(p) < fScore.get(currLowest)) {
               currLowest = p;
           }
        }
        return currLowest;
    }

    private Point[] getNeighbors(Point current) {
        Point[] neighbors = {
                new Point(current.x - 1, current.y),
                new Point(current.x, current.y - 1),
                new Point(current.x + 1, current.y),
                new Point(current.x, current.y + 1)
        };
        return neighbors;
    }

    private boolean isImpassable(Point pt) {
        return WorldController._instance.getTileTypeOfGivenTile(pt.x, pt.y).ID == 0;
    }

    private int getChangeInHealthFromTile(Point pt) {
        TileType tileType =  WorldController._instance.getTileTypeOfGivenTile(pt.x, pt.y);
        int change = 0;
        for(AbstractEffect ae : tileType.getEffects()) {
            if(ae.effectsHealth()) {
                change += ae.getMagnitude();
            }
        }
        return change;
    }
}
