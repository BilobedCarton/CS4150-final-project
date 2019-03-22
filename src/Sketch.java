import World.WorldController;
import processing.core.PApplet;

public class Sketch extends PApplet {

  public static String seed = "";
  private static WorldController wc;

  public void settings() {
    size(600, 600);
    wc = new WorldController(30, 30, seed, this);
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