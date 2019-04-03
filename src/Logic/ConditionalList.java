package Logic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConditionalList implements Comparable {
    private final List<Conditional> conditionalList;
    private int highestConditionalNumber;

    public ConditionalList() {
        conditionalList = new LinkedList<>();
    }

    public void add(Conditional newConditional) {
        conditionalList.add(newConditional);
        Collections.sort(conditionalList);
    }

    @Override
    public int compareTo(Object o) {
        if (conditionalList.size() == 0)
            return 0;
        else {
            ConditionalList otherConditionalList = ((ConditionalList) o);
            Conditional firstConditional = conditionalList.get(0);
            Conditional otherFirstConditional = otherConditionalList.get(0);

            return firstConditional.compareTo(otherFirstConditional);

        }
    }

    public Conditional get(int i) {
        return conditionalList.get(i);
    }

    @Override
    public String toString() {
        String stringToReturn = "";
        for (Conditional conditional : conditionalList)
            stringToReturn = stringToReturn + conditional.toString();
        return stringToReturn;

    }

    public void setNumbers(int firstNumber) {
        int highestConditionalNumber = 0;
        for (int i = 0; i < conditionalList.size(); i++) {
            conditionalList.get(i).setNumber(firstNumber + i);
            highestConditionalNumber++;
        }
        this.highestConditionalNumber = highestConditionalNumber;
    }

    public int getHighestConditionalNumber() {
        return highestConditionalNumber;
    }
}
