package Logic;

import java.util.LinkedList;
import java.util.List;

public class World implements Comparable {

    List<Integer> worlds;

    public World World() {
        worlds = new LinkedList<>();
    }

    public World(List worlds) {
        this.worlds = worlds;

    }


    @Override
    public int compareTo(Object o) {
        World otherWorld = (World) o;

        if (worlds.size() < otherWorld.getWorldsList().size())
            return -1;
        if (worlds.size() > otherWorld.getWorldsList().size())
            return 1;

        for (int i = 0; i < worlds.size(); i++) {
            if (worlds.get(i) > otherWorld.getWorldsList().get(i))
                return -1;
            if (worlds.get(i) < worlds.get(i))
                return 1;

        }
        System.out.println("comparing conditionals failed!!!");
        return 0;


    }


    public List<Integer> getWorldsList() {
        return worlds;
    }

    public int getSize() {
        return worlds.size();
    }

    public void add(int worldToAdd) {
        worlds.add(worldToAdd);
    }
}
