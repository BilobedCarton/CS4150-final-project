import World.WorldController;
import processing.core.PApplet;

public class Sketch extends PApplet {

  public static String seed = "";
  // This is in terrain cells (not pixels)
  private static int MAP_WIDTH = 30;
  private static int MAP_HEIGHT = 30;
  private static WorldController wc;

  public void settings() {
    size(600, 600);
    wc = new WorldController(MAP_WIDTH, MAP_HEIGHT, seed, this);
  }

  public void draw() {
    wc.draw();
  }

  public static void main(String[] args) {
    seed = args[0].equals("") ? "TEST" : args[0];
    String[] processingArgs = {"Sketch"};
    Sketch sketch = new Sketch();
    PApplet.runSketch(processingArgs, sketch);
  }

}