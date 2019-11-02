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
        if (conditionalList.size() == 0)
            return 0;
        if (!(o instanceof ConditionalList))
            throw new RuntimeException("Cant compare " + o.getClass() + " to ConditionalList");
        else {
            ConditionalList otherConditionalList = ((ConditionalList) o);
            WConditional firstConditional = conditionalList.get(0);
            WConditional otherFirstConditional = otherConditionalList.get(0);

            return firstConditional.compareTo(otherFirstConditional);

        }
    }

    public WConditional get(int i) {
        return conditionalList.get(i);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (WConditional conditional : conditionalList)
            sb.append(conditional.toString());
        return sb.toString();

    }

    public void setNumberToFirstConditional(int number) {
        conditionalList.get(0).setNumber(number);
    }

    public void setNumbersToEquivalentConditionals(int numberForFirstConditional) {
        nextConditionalNumber= numberForFirstConditional;
        for (int i = 1; i < conditionalList.size(); i++) {
            conditionalList.get(i).setNumber(nextConditionalNumber);
            nextConditionalNumber++;
        }
    }

    public int getNextConditionalNumber() {
        return nextConditionalNumber;
    }

    public List<WConditional> getList() {
        return conditionalList;
    }
}
