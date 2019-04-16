
package World.Entities;
import java.awt.*;

import World.WorldController;
import processing.core.PApplet;

public class Projectiles {

  private int currentx;
  private int currenty;

  private int changex;
  private int changey;

  Projectiles(int currentx, int currenty, int changex, int changey) {
    this.currentx = currentx;
    this.currenty = currenty;
    this.changex = changex;
    this.changey = changey;
  }



  public void draw() {
    PApplet sketch = WorldController._instance.sketch;
    sketch.ellipse(currentx, currenty, 10, 10);
    currentx += changex;
    currenty += changey;
  }

  public int getCurrentx() {
    return currentx;
  }

  public int getCurrenty() {
    return currentx;
  }


  public boolean onScreen() {
    return currentx < 0 || currenty < 0;
  }
}
