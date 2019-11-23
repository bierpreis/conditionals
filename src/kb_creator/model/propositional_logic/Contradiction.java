package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.signature.worlds.AbstractWorld;

public class Contradiction extends AbstractFormula {


    public Contradiction(){

    }
    @Override
    public boolean evaluate(AbstractWorld world) {
        return false;
    }

    @Override
    public AbstractFormula neg() {
        return new Tautology();
    }

    @Override
    public String toString() {
        return "(false)";
    }

}
