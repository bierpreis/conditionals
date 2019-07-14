package kb_creator.model.propositional_logic;


import kb_creator.model.propositional_logic.worlds.AbstractWorld;

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

        if (!(o instanceof Conjunction))
            return false;


        Conjunction otherConjunction = (Conjunction) o;


        if (formulas.size() != otherConjunction.getFormulas().size())
            return false;

        for (AbstractFormula formula : formulas)
            if (!otherConjunction.getFormulas().contains(formula))
                return false;
        for (AbstractFormula formula : otherConjunction.getFormulas())
            if (!formulas.contains(formula))
                return false;



        return true;

    }


    public List<AbstractFormula> getFormulas() {
        return formulas;
    }
}
