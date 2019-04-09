package World.Effects;

import World.Entities.AbstractEntity;
import World.WorldController;

import java.util.List;

public class ResetEffect extends AbstractEffect {
    public ResetEffect() {}

    @Override
    public void applyEffect(int x, int y) {
        List<AbstractEntity> entities = WorldController._instance.getEntitiesOnTile(x, y);
        for(AbstractEntity entity : entities) {
            entity.resetSpeed();
        }
    }

}
