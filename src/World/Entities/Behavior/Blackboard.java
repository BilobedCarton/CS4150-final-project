package World.Entities.Behavior;

import java.util.HashMap;

public class Blackboard {
    HashMap<String, Object> lookup;

    public Blackboard() {
        this.lookup = new HashMap<>();
    }

    public Object get(String key) {
        return lookup.get(key);
    }

    public Object getOrNull(String key) {
        if(lookup.containsKey(key)) {
            return this.get(key);
        }
        return null;
    }

    public void put(String key, Object val) {
        lookup.put(key, val);
    }
}
