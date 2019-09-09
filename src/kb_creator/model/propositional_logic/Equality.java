package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equality extends AbstractFormula {
    private List<AbstractFormula> formulaList;

    public Equality(AbstractFormula... formulas) {
        formulaList = new ArrayList<>(formulas.length);
        formulaList.addAll(Arrays.asList(formulas));
    }

    @Override
    public boolean evaluate(AbstractWorld world) {
        for (AbstractFormula formula : formulaList)
            if (!formula.evaluate(world) == formulaList.get(0).evaluate(world))
                return false;
        return true;

    }

    @Override
    public AbstractFormula neg() {
        return new Negation(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < formulaList.size(); i++) {
            sb.append(formulaList.get(i));
            if (i != formulaList.size() - 1)
                sb.append("==");
        }
        return sb.toString();
    }

    //todo: override eq
}
