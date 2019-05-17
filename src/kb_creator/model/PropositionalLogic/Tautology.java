package kb_creator.model.PropositionalLogic;

public class Tautology extends AbstractFormula {

    @Override
    public boolean evaluate(PossibleWorldABC interpretation) {
        return true;
    }

    public String toString() {
        return "(true)";
    }

    public boolean equals(Object o) {
        if (o instanceof Tautology)
            return true;
        else return false;
    }
}
