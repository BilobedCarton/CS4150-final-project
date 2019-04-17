package World.Entities.Behavior;

import World.Entities.Behavior.General.*;

public class BehaviorTreeLibrary {

    public static AbstractBehavior generateSimpleRanged(Blackboard bb) {
        return new Sequence(
                bb, new Selector(
                        bb, new IsEngaged(bb), new Engage(bb)
                ),
                new Parallel(bb,
                    new FacePlayer(bb),
                    new Selector(
                        bb, new CanSeePlayer(bb), new Sequence(
                                bb, new BuildPathToPlayer(bb), new HasPathToPlayer(bb), new PickRangedSpot(bb), new MoveAlongPath(bb)
                        )
                    ),
                    new Sequence(
                            bb, new CanSeePlayer(bb), new Selector(
                                    bb, new CanShoot(bb), new Reload(bb)),
                            new Selector(
                                    bb, new Sequence(bb, new CanShoot(bb), new Shoot(bb)), new Flee(bb)
                            )
                    )
                )
        );
    }

    public static AbstractBehavior generateSimpleMelee(Blackboard bb) {
        return new Sequence(
                bb, new Selector(
                        bb, new IsEngaged(bb), new Engage(bb)
                ),
                new Parallel(
                        bb, new FacePlayer(bb),
                        new Selector(
                                bb, new IsAdjacentToPlayer(bb), new Sequence(
                                        bb, new BuildPathToPlayer(bb), new HasPathToPlayer(bb), new PickMeleeSpot(bb), new MoveAlongPath(bb)
                                )
                        )
                ),
                new Sequence(
                        bb, new CanMeleeAttack(bb), new MeleeAttack(bb)
                )
        );
    }
}
