package kb_creator.model.PropositionalLogic;

import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.PossibleWorld;

public class Tautology extends AbstractFormula {

    @Override
    public boolean evaluate(PossibleWorld world) {
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
