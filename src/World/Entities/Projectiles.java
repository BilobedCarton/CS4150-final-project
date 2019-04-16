
package World.Entities;
import java.awt.*;

import World.WorldController;
import processing.core.PApplet;

public class Projectiles {

  private int currentx;
  private int currenty;

  private int changex;
  private int changey;

  public boolean hitSomething;

  Projectiles(int currentx, int currenty, int changex, int changey) {
    this.currentx = currentx;
    this.currenty = currenty;
    this.changex = changex;
    this.changey = changey;
    hitSomething = false;
  }



  public void draw() {
    PApplet sketch = WorldController._instance.sketch;
    sketch.ellipse(currentx, currenty, 10, 10);
  }

  public int getCurrentx() {
    return currentx;
  }

  public int getCurrenty() {
    return currentx;
  }

  public void update() {
    currentx += changex;
    currenty += changey;
  }

  public void setHitSomething() {
    currentx = 10000;
    currenty = 10000;
  }


  public boolean onScreen() {
    return currentx < 0 || currenty < 0;
  }
}
