import java.util.Arrays;
import java.util.List;
import java.util.Random;

import World.Entities.Mobs.Melee;
import World.Entities.Mobs.Ranged;
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
    frameRate = 60;
    WorldController.reset();
  }

  public void draw() {
    wc.draw();
  }

  public void mouseReleased() {
    if(mouseButton == LEFT) {
      WorldController._instance.addMob(new Melee(mouseX / CELL_DIMENSION, mouseY / CELL_DIMENSION));
    }
    else if(mouseButton == RIGHT) {
      WorldController._instance.addMob(new Ranged(mouseX / CELL_DIMENSION, mouseY / CELL_DIMENSION));
    }
  }

  public void keyReleased() {
    if(validInputs.contains(key)) {
      wc.player.parseInput(key);
      wc.executeTick();
    }
    else if (key == 'z') {
      wc.DEBUG_MODE = !wc.DEBUG_MODE;
    }
    else if (key == 'r') {
      WorldController.reset();
    }
  }

  public static void main(String[] args) {
    String seed = args.length == 0 ? "" : args[0];
    String[] processingArgs = {"Sketch"};
    Sketch sketch = new Sketch(seed);
    PApplet.runSketch(processingArgs, sketch);
  }
}