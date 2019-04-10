package World.Effects;

import World.WorldController;

public class WinEffect extends AbstractEffect {
    @Override
    public void applyEffect(int x, int y) {
        WorldController._instance.incrementScore();
        WorldController._instance.resetMap = true;
    }
}
