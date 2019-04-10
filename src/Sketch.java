import java.util.Random;

import World.WorldController;
import processing.core.PApplet;
import processing.event.Event;

public class Sketch extends PApplet {

  private Random rand;
  private static int MAP_WIDTH = 1200;
  private static int MAP_HEIGHT = 645;
  private static int CELL_DIMENSION = 15;
  private static WorldController wc;

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
    wc.player.movePlayer();
    wc.draw();
  }

  public void mouseReleased() {
    this.wc = new WorldController(MAP_WIDTH / CELL_DIMENSION, MAP_HEIGHT / CELL_DIMENSION, CELL_DIMENSION, rand, this);
    WorldController.reset();
  }

  public void keyPressed() {
    this.wc.player.setMove(keyCode, true);
  }

  public void keyReleased() {
    this.wc.player.setMove(keyCode, false);
  }

  public static void main(String[] args) {
    String seed = args.length == 0 ? "" : args[0];
    String[] processingArgs = {"Sketch"};
    Sketch sketch = new Sketch(seed);
    PApplet.runSketch(processingArgs, sketch);
  }
}