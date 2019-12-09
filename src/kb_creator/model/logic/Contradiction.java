package kb_creator.model.logic;

import kb_creator.model.logic.signature.worlds.AbstractWorld;

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
