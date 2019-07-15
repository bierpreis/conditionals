package kb_creator.model.conditionals;

import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.Atom;
import kb_creator.model.propositional_logic.Conjunction;
import kb_creator.model.propositional_logic.Variable;
import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import nfc.model.Conditional;
import nfc.model.World;


public class NewConditional {
    private int number;
    private final AbstractFormula antecend;
    private final AbstractFormula consequence;
    private NewConditional counterConditional;


    public NewConditional(AbstractFormula consequence, AbstractFormula antecend) {

        this.consequence = consequence;
        this.antecend = antecend;
    }

    public NewConditional(Conditional oldConditional) {
        World oldAntecend = oldConditional.getAntecedent();
        World oldConsequence = oldConditional.getConsequence();
        antecend = worldToFormula(oldAntecend);
        consequence = worldToFormula(oldConsequence);
        number = oldConditional.getNumber();
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

                } //todo: conjunction is AND. here and and or are mixed?!?!?! same in abc!!!
                //actualy this is possible. its sth lie ab, aNOTb. is it like that? rethink and delete.
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
                else formulaToReturn = formulaToReturn.or(conjunctionToAdd);

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
        if (this.number == 0 || otherConditional.getNumber() == 0)
            throw new RuntimeException("Equals failed because numbers were wrong");
        //return this.antecend.equals(otherConditional.getAntecend()) && this.consequence.equals(otherConditional.getConsequence());
        return this.number == otherConditional.getNumber();

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


    public void setBasicCounterConditional(Conditional oldCounterConditional) {
        this.counterConditional = new NewConditional(oldCounterConditional);
        this.counterConditional.setNumber(oldCounterConditional.getNumber());
    }


    public void setActualCounterConditional(NewConditional counterConditional) {
        this.counterConditional = counterConditional;
    }

}