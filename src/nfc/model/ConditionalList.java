package nfc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConditionalList implements Comparable {
    private final List<WConditional> conditionalList;
    private int nextConditionalNumber;

    public ConditionalList() {
        conditionalList = new ArrayList<>();
    }

    public void add(WConditional newConditional) {
        conditionalList.add(newConditional);
        Collections.sort(conditionalList);

    }

    @Override
    public int compareTo(Object o) {
        ConditionalList otherConditionalList = ((ConditionalList) o);
        WConditional firstConditional = conditionalList.get(0);
        WConditional otherFirstConditional = otherConditionalList.get(0);

        return firstConditional.compareTo(otherFirstConditional);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (WConditional conditional : conditionalList)
            sb.append(conditional.toString());
        return sb.toString();

    }

    public void setNumberToFirstConditional(int number) {
        conditionalList.get(0).setNumber(number); //todo: this overwrites number?!
    }

    public void setNumbersToEquivalentConditionals(int numberForFirstConditional) {
        nextConditionalNumber = numberForFirstConditional;
        for (int i = 1; i < conditionalList.size(); i++) {
            conditionalList.get(i).setNumber(nextConditionalNumber);
            nextConditionalNumber++;
        }
    }

    //getters

    public int getNextConditionalNumber() {
        return nextConditionalNumber;
    }

    public List<WConditional> getList() {
        return conditionalList;
    }

    public WConditional get(int i) {
        return conditionalList.get(i);
    }
}
