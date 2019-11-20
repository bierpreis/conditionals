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

    public Equality(List<AbstractFormula> formulaList) {
        this.formulaList = formulaList;
    }

    @Override
    public boolean evaluate(AbstractWorld world) {
        for (AbstractFormula formula : formulaList)
            if (!formula.evaluate(world) == formulaList.get(0).evaluate(world))
                return false;
        return true;

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

    @Override
    public AbstractFormula eq(AbstractFormula otherFormula) {
        List<AbstractFormula> newFormulaList = new ArrayList<>(formulaList.size() + 1);
        newFormulaList.addAll(this.formulaList);
        newFormulaList.add(otherFormula);
        return new Equality(newFormulaList);
    }
}
