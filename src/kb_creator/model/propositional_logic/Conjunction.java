package kb_creator.model.propositional_logic;


import kb_creator.model.propositional_logic.worlds.AbstractWorld;

import java.util.*;

public class Conjunction extends AbstractFormula {
    private List<AbstractFormula> formulaList;

    public Conjunction(AbstractFormula... formulasToAdd) {


        formulaList = new ArrayList<>(formulasToAdd.length);
        formulaList.addAll(Arrays.asList(formulasToAdd));
    }

    @Override
    public boolean evaluate(AbstractWorld world) {
        boolean evaluation = true;
        for (AbstractFormula formula : formulaList) {
            evaluation = evaluation && formula.evaluate(world);
        }
        return evaluation;
    }

    @Override
    public AbstractFormula neg() {
        return new Negation(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractFormula formula : formulaList) {
            sb.append(formula.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Conjunction))
            return false;


        Conjunction otherConjunction = (Conjunction) o;


        for (AbstractFormula formula : formulaList)
            if (!otherConjunction.getFormulaList().contains(formula))
                return false;
        for (AbstractFormula formula : otherConjunction.getFormulaList())
            if (!formulaList.contains(formula))
                return false;


        return true;

    }

    @Override
    public AbstractFormula and(AbstractFormula otherFormula) {
        if (otherFormula instanceof Conjunction) {
            formulaList.addAll(((Conjunction) otherFormula).getFormulaList());
            return this;
        } else return super.and(otherFormula);
    }


    public List<AbstractFormula> getFormulaList() {
        return formulaList;
    }
}
