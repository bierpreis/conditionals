package kb_creator.model.PropositionalLogic;

import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.PossibleWorld;

public class Atom extends AbstractFormula {
    private Variable variable;

    public Atom(Variable variable) {
        this.variable = variable;
    }

    public Variable get() {
        return variable;
    }

    @Override
    public String toString() {
        return variable.toString();
    }
    
    public boolean evaluate(PossibleWorld world) {
        if (variable.equals(Variable.a))
            return world.isA();

        if (variable.equals(Variable.b))
            return world.isB();

        if (variable.equals(Variable.c))
            return world.isC();

        else throw new RuntimeException("No possibleWorld found!");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Atom))
            return false;
        Atom otherAtom = (Atom) o;
        return otherAtom.get().equals(variable);
    }
}
