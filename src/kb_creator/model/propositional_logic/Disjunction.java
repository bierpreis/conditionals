package kb_creator.model.propositional_logic;

import kb_creator.model.propositional_logic.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Disjunction extends AbstractFormula {

    private List<AbstractFormula> formulaList;


    public Disjunction(AbstractFormula... formulasToAdd) {
        formulaList = new ArrayList<>(formulasToAdd.length);
        formulaList.addAll(Arrays.asList(formulasToAdd));
    }

    @Override
    public boolean evaluate(AbstractWorld world) {
        for (AbstractFormula formula : formulaList) {
            if (formula.evaluate(world))
                return true;
        }
        return false;
    }

    @Override
    public AbstractFormula neg() {
        return new Negation(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < formulaList.size(); i++) {
            sb.append(formulaList.get(i).toString());
            if (i != (formulaList.size() - 1))
                sb.append(", ");
        }
        return sb.toString();
    }

}
