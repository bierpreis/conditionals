package kb_creator.model.PropositionalLogic;


import kb_creator.model.PropositionalLogic.Worlds.AbstractWorld;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Conjunction extends AbstractFormula {
    private HashSet<AbstractFormula> formulas;

    public Conjunction(AbstractFormula... formulasToAdd) {

        //with hash set they are not sorted anymore?!
        formulas = new LinkedHashSet<>();
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
        if (!(o instanceof Conjunction))
            return false;
        Conjunction otherConjunction = (Conjunction) o;

        //todo: equals doenst work
        boolean equals = (otherConjunction.getFormulas().equals(formulas));
        if (equals) {
            System.out.println("equals:");
            System.out.println(formulas);

        } else {
            System.out.println("not equals: ");
            System.out.println(formulas);
            System.out.println(otherConjunction.getFormulas());
        }
        return equals;
    }

    public Set<AbstractFormula> getFormulas() {
        return formulas;
    }
}
