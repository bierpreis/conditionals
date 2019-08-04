package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

public class Contradiction extends AbstractFormula {


    @Override
    public boolean evaluate(AbstractWorld world) {
        return false;
    }

    @Override
    public AbstractFormula neg() {
        return new Tautology();
    }

    //todo
    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Contradiction;
    }
}
