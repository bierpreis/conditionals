package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

public class Tautology extends AbstractFormula {

    @Override
    public boolean evaluate(AbstractWorld world) {
        return true;
    }

    public String toString() {
        return "(true)";
    }

    public boolean equals(Object o) {
        return (o instanceof Tautology);
    }
}
