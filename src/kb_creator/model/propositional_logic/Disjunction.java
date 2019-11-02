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

    public Disjunction(List<AbstractFormula> formulaList) {
        this.formulaList = formulaList;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < formulaList.size(); i++) {
            sb.append(formulaList.get(i).toString());
            if (i != (formulaList.size() - 1))
                sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public AbstractFormula or(AbstractFormula otherFormula) {
        List<AbstractFormula> newFormulaList = new ArrayList<>(formulaList.size() + 1);
        newFormulaList.addAll(this.formulaList);
        newFormulaList.add(otherFormula);
        return new Disjunction(newFormulaList);
    }

}
