package kb_creator.model.PropositionalLogic;

import kb_creator.model.Signature.ABC;

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

    public boolean evaluate(ABC interpretation) {
        if (variable.equals(Variable.a))
            return interpretation.isA();

        if (variable.equals(Variable.b))
            return interpretation.isB();

        if (variable.equals(Variable.c))
            return interpretation.isC();

        else throw new RuntimeException("No interpretation found!");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Atom))
            return false;
        Atom otherAtom = (Atom) o;
        return otherAtom.get().equals(variable);
    }
}
