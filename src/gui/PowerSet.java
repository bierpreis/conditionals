package gui;

import java.util.*;

public class PowerSet {
    public static void main(String[] args) {
        int set[] = new int[8];
        for (int i = 0; i < set.length; i++) {
            set[i] = i;
        }

        List<Set> sets = new LinkedList<>();
        sets.add(new TreeSet());

        for (int currentWorld : set) {
            for (int i = 7; i > 0; i--) {
                Set newSet = new TreeSet<>(Collections.reverseOrder());
                newSet.add(currentWorld);
                newSet.add(set[i]);
                sets.add(newSet);
            }

        }

        System.out.println(sets);

    }
}
