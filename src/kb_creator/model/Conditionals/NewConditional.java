package kb_creator.model.Conditionals;

import kb_creator.model.PropositionalLogic.AbstractFormula;
import kb_creator.model.PropositionalLogic.Atom;
import kb_creator.model.PropositionalLogic.Conjunction;
import kb_creator.model.PropositionalLogic.Variable;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import nfc.model.Conditional;
import nfc.model.World;


public class NewConditional {
    private int number;
    private AbstractFormula antecend;
    private AbstractFormula consequence;

    public NewConditional(AbstractFormula antecend, AbstractFormula consequence) {
        this.antecend = antecend;
        this.consequence = consequence;
    }

    public NewConditional(Conditional oldConditional) {
        World oldAntecend = oldConditional.getAntecedent();
        World oldConsequence = oldConditional.getConsequence();
        antecend = worldToFormula(oldAntecend);
        consequence = worldToFormula(oldConsequence);
        number = oldConditional.getNumber();
        //todo: maybe here set counter conditional again?
    }


    private AbstractFormula worldToFormula(World world) {
        AbstractFormula formulaToReturn = null;

        if (world.getSignature() instanceof AB) {

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
                else formulaToReturn = formulaToReturn.or(new Conjunction(firstAtom, secondAtom));
            }

        } else if (world.getSignature() instanceof ABC) {

            for (int worldInt : world.getWorldsList()) {
                AbstractFormula firstAtom = new Atom(Variable.a);
                AbstractFormula secondAtom = new Atom(Variable.b);
                AbstractFormula thirdAtom = new Atom(Variable.c);

                switch (worldInt) {
                    case 0:
                        firstAtom = firstAtom.neg();
                        secondAtom = secondAtom.neg();
                        thirdAtom = thirdAtom.neg();
                        break;
                    case 1:
                        firstAtom = firstAtom.neg();
                        secondAtom = secondAtom.neg();
                        break;
                    case 2:
                        firstAtom = firstAtom.neg();
                        thirdAtom = thirdAtom.neg();

                        break;
                    case 3:
                        firstAtom = firstAtom.neg();
                        break;

                    case 4:
                        secondAtom = secondAtom.neg();
                        thirdAtom = thirdAtom.neg();
                        break;
                    case 5:
                        secondAtom = secondAtom.neg();
                        break;
                    case 6:
                        thirdAtom = thirdAtom.neg();
                        break;
                    case 7:
                        break;
                    default:
                        throw new RuntimeException("Unknown World: " + worldInt);

                }

                Conjunction conjunctionToAdd = new Conjunction(firstAtom, secondAtom, thirdAtom);
                if (formulaToReturn == null)
                    formulaToReturn = conjunctionToAdd;
                else formulaToReturn.and(conjunctionToAdd);

            }

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


    //todo: does this really work? does this counter and the real counter equal??
    public NewConditional getCounterConditional() {
        return new NewConditional(this.consequence.neg(), this.antecend);
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