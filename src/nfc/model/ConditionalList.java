package nfc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConditionalList implements Comparable {
    private final List<Conditional> conditionalList;
    private int highestConditionalNumber;


    public ConditionalList() {
        conditionalList = new ArrayList<>();
    }

    public void add(Conditional newConditional) {
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

    public void setNumberToFirstConditional(int number) {
        conditionalList.get(0).setNumber(number);
    }

    //todo: this must be wrong
    public void setNumbersToEquivalentConditionals(int hightestNumberOfLastList) {
        int conditionalNumber = hightestNumberOfLastList;
        for (int i = 1; i < conditionalList.size(); i++) {
            conditionalList.get(i).setNumber(conditionalNumber);
            conditionalNumber++;
        }
        highestConditionalNumber = conditionalNumber;
    }

    public int getHighestConditionalNumber() {
        return highestConditionalNumber;
    }

    public List<Conditional> getList() {
        return conditionalList;
    }
}
