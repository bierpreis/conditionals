package New;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class World {
    private List<Integer> props = new LinkedList<>();


    public World(){

    }
    public void sort() {
        Collections.sort(props);
    }
}
