package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

public class Tautology extends AbstractFormula {

    @Override
    public boolean evaluate(AbstractWorld world) {
        return true;
    }

    @Override
    public String toString() {
        return "(true)";
    }

    @Override
    public AbstractFormula neg() {
        return new Contradiction();
    }
}
