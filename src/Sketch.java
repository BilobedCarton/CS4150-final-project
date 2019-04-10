import java.util.Arrays;
import java.util.List;
import java.util.Random;

import World.WorldController;
import processing.core.PApplet;

public class Sketch extends PApplet {

  private Random rand;
  private static int MAP_WIDTH = 1200;
  private static int MAP_HEIGHT = 645;
  private static int CELL_DIMENSION = 15;
  private static WorldController wc;
  private final List<Character> validInputs = Arrays.asList(new Character[]{'w', 'a', 's', 'd'});

  public Sketch(String seed) {
    super();
    if(seed.equals("")) {
      this.rand = new Random();
    }
    else{
      this.rand = new Random(seed.hashCode());
    }
    this.wc = new WorldController(MAP_WIDTH / CELL_DIMENSION, MAP_HEIGHT / CELL_DIMENSION, CELL_DIMENSION, rand, this);
  }

  public void settings() {
    size(MAP_WIDTH, MAP_HEIGHT);
    WorldController.reset();
  }

  public void draw() {
    wc.draw();
  }

  public void mouseReleased() {
    this.wc = new WorldController(MAP_WIDTH / CELL_DIMENSION, MAP_HEIGHT / CELL_DIMENSION, CELL_DIMENSION, rand, this);
    WorldController.reset();
  }

  public void keyReleased() {
    if(validInputs.contains(key)) {
      wc.player.parseInput(key);
      wc.executeTick();
    }
  }

  public static void main(String[] args) {
    String seed = args.length == 0 ? "" : args[0];
    String[] processingArgs = {"Sketch"};
    Sketch sketch = new Sketch(seed);
    PApplet.runSketch(processingArgs, sketch);
  }
}