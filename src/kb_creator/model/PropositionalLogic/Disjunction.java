package kb_creator.model.PropositionalLogic;

import kb_creator.model.PropositionalLogic.Worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;

public class Disjunction extends AbstractFormula {
    private List<AbstractFormula> formulaList;


    public Disjunction(AbstractFormula... formulasToAdd) {
        formulaList = new ArrayList<>();
        for (AbstractFormula formula : formulasToAdd)
            formulaList.add(formula);
    }

    @Override
    public boolean evaluate(AbstractWorld world) {
        for (AbstractFormula formula : formulaList) {
            if (formula.evaluate(world))
                return true;
        }
        return false;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractFormula formua : formulaList) {
            sb.append(formua.toString());
            sb.append(", ");
        }
        //remove the ", " at the end
        return sb.toString().replaceAll(", $", "");
    }


    @Override
    public boolean equals(Object o) {
        //todo: this is not really correct
        //and think again about this
        if (!(o instanceof Disjunction))
            return false;
        for (AbstractFormula formula : formulaList) {
            if (!((Disjunction) o).getFormulaList().contains(formula))
                return false;
        }

        for (AbstractFormula formula : ((Disjunction) o).getFormulaList()) {
            if (!formulaList.contains(formula))
                return false;
        }
        return true;
    }

    public List<AbstractFormula> getFormulaList() {
        return formulaList;
    }
}
