package World.Effects;

import World.Entities.AbstractEntity;
import World.WorldController;

import java.util.List;

public class SlowEffect extends AbstractEffect {
    private double multiplier;

    public SlowEffect(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void applyEffect(int x, int y) {
        List<AbstractEntity> entities = WorldController._instance.getEntitiesOnTile(x, y);
        for(AbstractEntity entity : entities) {
            entity.modifySpeed(multiplier);
        }
    }
}
