package kb_creator.model.propositional_logic;


import kb_creator.model.propositional_logic.worlds.AbstractWorld;

import java.util.*;

public class Conjunction extends AbstractFormula {
    private List<AbstractFormula> formulaList;

    public Conjunction(AbstractFormula... formulasToAdd) {


        formulaList = new ArrayList<>(formulasToAdd.length);
        formulaList.addAll(Arrays.asList(formulasToAdd));
    }

    //if one of the list is false, the whole conjunction is false
    //returning false immediately is a bit faster than always calculating all elements before returning the value
    @Override
    public boolean evaluate(AbstractWorld world) {
        for (AbstractFormula formula : formulaList) {
            if (!formula.evaluate(world))
                return false;
        }
        return true;
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

    //this is not really correct but should work in all cases it will be used
    @Override
    public boolean equals(Object o) {
        System.out.println("equals was called!!");
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
