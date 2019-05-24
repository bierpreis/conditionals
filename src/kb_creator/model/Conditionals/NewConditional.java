package kb_creator.model.Conditionals;

import kb_creator.model.PropositionalLogic.AbstractFormula;
import kb_creator.model.PropositionalLogic.Atom;
import kb_creator.model.PropositionalLogic.Conjunction;
import kb_creator.model.PropositionalLogic.Variable;
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
        this.number = oldConditional.getNumber(); //todo: make comparable with this number. then sort kbs to see possible problems?

    }

    //todo: implement abc signature. maybe put into signature?
    private AbstractFormula worldToFormula(World world) {
        AbstractFormula formulaToReturn = null;
        for (int worldInt : world.getWorldsList()) {
            AbstractFormula firstAtom = new Atom(Variable.a);
            AbstractFormula secondAtom = new Atom(Variable.b);
            switch (worldInt) {
                case 0:
                    firstAtom = firstAtom.neg();
                    secondAtom = secondAtom.neg();
                    break;
                case 1:
                    firstAtom = firstAtom.neg();
                    break;
                case 2:
                    secondAtom = secondAtom.neg();
                    break;
                case 3:
                    break;
                default:
                    throw new RuntimeException("Unknown World: " + worldInt);

            }
            if (formulaToReturn == null)
                formulaToReturn = new Conjunction(firstAtom, secondAtom);
                //todo: this cant be correct. or ???
            else formulaToReturn = formulaToReturn.or(new Conjunction(firstAtom, secondAtom));

        }
        return formulaToReturn;
    }

    public String toString() {
        return "(" + consequence + " | " + antecend + ")";
    }

    public int getNumber() {
        return number;
    }


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