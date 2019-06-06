package kb_creator.model.PropositionalLogic;


import kb_creator.model.PropositionalLogic.Worlds.AbstractWorld;

import java.util.*;

public class Conjunction extends AbstractFormula {
    private List<AbstractFormula> formulas;

    public Conjunction(AbstractFormula... formulasToAdd) {


        formulas = new ArrayList<>();
        for (AbstractFormula formula : formulasToAdd) {
            formulas.add(formula);

        }
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
        //todo: this is not really correct.
        if (!(o instanceof Conjunction))
            return false;


        Conjunction otherConjunction = (Conjunction) o;

        boolean equals;
        if (formulas.size() == otherConjunction.getFormulas().size()) {
            equals = true;
            for (AbstractFormula formula : formulas) {
                if (!otherConjunction.getFormulas().contains(formula))
                    equals = false;
            }
        } else equals = false;

        return equals;
    }

    public List<AbstractFormula> getFormulas() {
        return formulas;
    }
}
