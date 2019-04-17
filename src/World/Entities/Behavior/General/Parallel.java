package World.Entities.Behavior.General;

import World.Entities.Behavior.AbstractBehavior;
import World.Entities.Behavior.Blackboard;
import World.WorldController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Parallel extends AbstractBehavior {
    private class BehaviorRunnable implements Runnable {
        private AbstractBehavior behavior;

        public BehaviorRunnable(AbstractBehavior behavior) {
            this.behavior = behavior;
        }

        @Override
        public void run() {
            runningChildren.add(behavior);
            int returned = this.behavior.execute();
            runningChildren.remove(behavior);
            if(returned == 0) {
                result = 0;
                for(Thread t : threads) {
                    if(t.getId() != Thread.currentThread().getId()) {
                        t.interrupt();
                        latch.countDown();
                    }
                }
            }
            else if(runningChildren.size() == 0) {
                result = 1;
            }
            latch.countDown();
        }
    }

    private List<AbstractBehavior> children;
    private List<AbstractBehavior> runningChildren = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();
    private int result = -1;
    private CountDownLatch latch;

    public Parallel(Blackboard bb, AbstractBehavior... children) {
        super(bb);
        this.children = new ArrayList<AbstractBehavior>(Arrays.asList(children));
    }

    @Override
    public AbstractBehavior duplicateWithNewBlackboard(Blackboard bb) {
        for(int i = 0; i < children.size(); i++) {
            children.add(children.remove(0).duplicateWithNewBlackboard(bb));
        }
        this.bb = bb;
        return this;
    }

    @Override
    public int execute() {
        if(WorldController._instance.DEBUG_MODE) {
            System.out.println("Debug - Executing tasks in parallel");
        }
        latch = new CountDownLatch(children.size());
        for(AbstractBehavior child : children) {
            BehaviorRunnable childRunnable = new BehaviorRunnable(child);
            Thread thread = new Thread(childRunnable);
            threads.add(thread);
            thread.start();
        }
        try {
            latch.await();
        }
        catch(InterruptedException e) {
            return 0;
        }
        return result;
    }
}
