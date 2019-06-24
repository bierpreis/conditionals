package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.Worlds.AbstractWorld;

public class Tautology extends AbstractFormula {

    @Override
    public boolean evaluate(AbstractWorld world) {
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