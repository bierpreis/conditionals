package gui;

import java.util.LinkedList;
import java.util.List;

public class PowerSet {
    public static void main(String[] args) {
        int set[] = new int[8];
        for (int i = 0; i < set.length; i++) {
            set[i] = i;
        }

        List<List> sets = new LinkedList<>();
        sets.add(new LinkedList());

        for (int currentWorld : set) {
            for (int i = 0; i < 7; i++) {
                List newList = new LinkedList<>();
                newList.add(currentWorld);
                newList.add(set[i]);
                sets.add(newList);
                System.out.println(currentWorld);
            }

        }

        System.out.println(sets);

    }
}
