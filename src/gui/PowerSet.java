package gui;

import java.util.*;

public class PowerSet {
    public static void main(String[] args) {
        List<Integer> niceList = new LinkedList<>();
        niceList.add(1);
        niceList.add(2);
        niceList.add(3);

        //own();
        List<List<Integer>> betterList = createSubSetList(niceList);
        System.out.println(betterList);

    }

    public static void own() {
        List<Integer> set = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            set.add(i);
        }

        List<Set> sets = new LinkedList<>();
        //sets.add(new TreeSet());

        for (int currentWorld : set) {
            for (int i = 7; i > 0; i--) {
                Set newSet = new TreeSet<>(Collections.reverseOrder());
                newSet.add(currentWorld);
                newSet.add(set);

                sets.add(newSet);
            }

        }

        System.out.println(sets);
        System.out.println(sets.size());
    }



    public static  List<List<Integer>> createSubSetList(List<Integer> input) {
        List<List<Integer>> sets = new ArrayList<>();
        for (Integer world : input) {
            for (ListIterator<List<Integer>> setsIterator = sets.listIterator(); setsIterator.hasNext(); ) {
                List<Integer> newSet = new ArrayList<>(setsIterator.next());
                newSet.add(world);
                setsIterator.add(newSet);
            }
            sets.add(new ArrayList<>(Arrays.asList(world)));
        }
        return sets;
    }
}
