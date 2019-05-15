package kb_creator.model.PropositionalLogic;

import nfc.model.Conditional;
import nfc.model.World;


public class NewConditional {
    private int number;
    private AbstractFormula antecend;
    private AbstractFormula consequence;

    private NewConditional counterConditional;

    public NewConditional(AbstractFormula antecend, AbstractFormula consequence) {
        this.antecend = antecend;
        this.consequence = consequence;
    }

    public NewConditional(Conditional oldConditional) {
        World oldAntecend = oldConditional.getAntecedent();
        World oldConsequence = oldConditional.getConsequence();
        antecend = worldToFormula(oldAntecend);
        consequence = worldToFormula(oldConsequence);
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
        return "(" + consequence + "|" + antecend + ")";
    }

    public int getNumber() {
        return number;
    }

    //todo: check if equals works properly
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NewConditional))
            return false;
        NewConditional otherConditional = (NewConditional) o;
        return this.antecend.equals(otherConditional.getAntecend()) && this.consequence.equals(otherConditional.getConsequence());

    }

    public void createCounterConditional(Conditional oldConditional) {
        counterConditional = new NewConditional(oldConditional);
    }

    public NewConditional getCounterConditional() {
        return counterConditional;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public AbstractFormula getAntecend() {
        return antecend;
    }

    public AbstractFormula getConsequence() {
        return consequence;
    }
}