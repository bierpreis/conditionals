package kb_creator.model.PropositionalLogic;

import nfc.model.Conditional;
import nfc.model.World;


public class NewConditional {
    private int number;
    private AbstractFormula antecend;
    private AbstractFormula consequent;

    public NewConditional(AbstractFormula antecend, AbstractFormula consequent) {
        this.antecend = antecend;
        this.consequent = consequent;
    }

    public NewConditional(Conditional oldConditional) {
        World oldAntecend = oldConditional.getAntecedent();
        World oldConsequence = oldConditional.getConsequence();
        antecend = worldToFormula(oldAntecend);
        consequent = worldToFormula(oldConsequence);
        this.number = oldConditional.getNumber();

    }


    private AbstractFormula worldToFormula(World world) {
        AbstractFormula formulaToReturn = null;
        for (int worldInt : world.getWorldsList()) {
            AbstractFormula firstAtom = new Atom(Variable.a);
            AbstractFormula secondAtom = new Atom(Variable.b);
            switch (worldInt) {


                case 0:

                    firstAtom.neg();
                    secondAtom.neg();

                    break;
                case 1:
                    firstAtom.neg();
                    break;
                case 2:
                    secondAtom.neg();
                    break;
                case 3:
                    break;
                default:
                    throw new RuntimeException("Unknown World: " + worldInt);

            }
            if (formulaToReturn == null)
                formulaToReturn = new Conjunction(firstAtom, secondAtom);
            else formulaToReturn = formulaToReturn.and(new Conjunction(firstAtom, secondAtom));

        }
        return formulaToReturn;
    }

    public String toString() {
        return "(" + consequent + " | " + antecend + ")";
    }

    public int getNumber() {
        return number;
    }

    //todo: equals method

    public void createCounterConditional(Conditional oldConditional){

    }

    public NewConditional getCounterConditional(){
        //todo: good counter option
        return new NewConditional(, antecend);
    }


}