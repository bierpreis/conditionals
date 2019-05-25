package kb_creator.model.PropositionalLogic;


import kb_creator.model.PropositionalLogic.Worlds.AbstractWorld;

import java.util.HashSet;
import java.util.Set;

//todo: conjunction needs to work with 3 formulas at least. this with parameter...
public class Conjunction extends AbstractFormula {
    private HashSet<AbstractFormula> formulas;

    public Conjunction(AbstractFormula... formulasToAdd) {
        formulas = new HashSet();
        for (AbstractFormula formula : formulasToAdd)
            formulas.add(formula);
    }

    @Override
    public boolean evaluate(AbstractWorld world) {
        boolean evaluation = true;
        for (AbstractFormula formula : formulas) {
            evaluation = evaluation && formula.evaluate(world);
        }
        return evaluation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractFormula formula : formulas) {
            sb.append(formula.toString());
        }
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Conjunction))
            return false;
        Conjunction otherConjunction = (Conjunction) o;

        //todo: check if this could work
        return (otherConjunction.getFormulas().equals(formulas));
    }

    public Set<AbstractFormula> getFormulas() {
        return formulas;
    }
}
