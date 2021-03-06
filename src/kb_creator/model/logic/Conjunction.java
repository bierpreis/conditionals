package kb_creator.model.logic;


import kb_creator.model.logic.world.AbstractWorld;

import java.util.*;

public class Conjunction extends AbstractFormula {
    private final List<AbstractFormula> formulaList;

    public Conjunction(AbstractFormula... formulasToAdd) {
        formulaList = new ArrayList<>(formulasToAdd.length);
        formulaList.addAll(Arrays.asList(formulasToAdd));
    }

    public Conjunction(List<AbstractFormula> formulaList) {
        this.formulaList = Collections.unmodifiableList(formulaList);
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
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i<formulaList.size(); i++){
            sb.append(formulaList.get(i).toString());
            if(i!=formulaList.size()-1)
            sb.append(",");
        }
        return sb.toString();
    }

    @Override
    public AbstractFormula and(AbstractFormula otherFormula) {
        List<AbstractFormula> newFormulaList = new ArrayList<>(formulaList.size() + 1);
        newFormulaList.addAll(this.formulaList);
        newFormulaList.add(otherFormula);
        return new Conjunction(newFormulaList);
    }

}
