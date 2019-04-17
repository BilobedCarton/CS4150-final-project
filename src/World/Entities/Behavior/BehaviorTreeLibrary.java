package World.Entities.Behavior;

import World.Entities.Behavior.General.*;

public class BehaviorTreeLibrary {

    public static AbstractBehavior generateSimpleRanged(Blackboard bb) {
        return new Sequence(
                bb, new Selector(
                        bb, new IsEngaged(bb), new Engage(bb)
                ),
                new Selector(
                        bb, new CanSeePlayer(bb), new Sequence(
                                bb, new BuildPathToPlayer(bb), new HasPathToPlayer(bb), new PickRangedSpot(bb), new MoveAlongPath(bb)
                        )
                ),
                new FacePlayer(bb)
        );
    }

    public static AbstractBehavior generateSimpleMelee(Blackboard bb) {
        return new Sequence(
                bb, new Selector(
                        bb, new IsEngaged(bb), new Engage(bb)
                ),
                new FacePlayer(bb),
                new Selector(
                        bb, new IsAdjacentToPlayer(bb), new Sequence(
                                bb, new BuildPathToPlayer(bb), new HasPathToPlayer(bb), new PickMeleeSpot(bb), new MoveAlongPath(bb)
                        )
                )
        );
    }
}
