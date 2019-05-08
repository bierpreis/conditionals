package kb_creator.model.NewConditional;

public class Atom extends AbstractFormula {
    private Variable variable;

    public Atom(Variable variable) {
        this.variable = variable;
    }

    public Variable get() {
        return variable;
    }

    public String toString() {
        return variable.toString();
    }

    public boolean evaluate(Interpretation interpretation) {
        if (variable.equals(Variable.a))
            return interpretation.isA();

        if (variable.equals(Variable.a))
            return interpretation.isA();

        if (variable.equals(Variable.a))
            return interpretation.isA();

        else throw new RuntimeException("No interpretation found!");
    }
}
