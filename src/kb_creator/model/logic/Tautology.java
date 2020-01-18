package kb_creator.model.logic;

import kb_creator.model.logic.world.AbstractWorld;

public class Tautology extends AbstractFormula {


    public Tautology(){

    }


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
