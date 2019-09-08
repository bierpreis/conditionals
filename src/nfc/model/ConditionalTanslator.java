package nfc.model;

import kb_creator.model.propositional_logic.*;
import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

public class ConditionalTanslator {
    private AbstractSignature signature;
    private ShortTranslationMap shortTranslationMap;

    public ConditionalTanslator(AbstractSignature signature) {
        shortTranslationMap = new ShortTranslationMap(signature);


        AbstractFormula.setSignature(signature);
    }


    public NewConditional transLate(Conditional oldConditional) {
        AbstractFormula newAntecend = worldToFormula(oldConditional.getAntecedent());
        AbstractFormula newConsequence = worldToFormula(oldConditional.getConsequence());

        //this is a test that translation really works
        if (!newAntecend.equals(oldWorldToFormula(oldConditional.getAntecedent())))
            throw new RuntimeException("Translating worlds failed! " + newAntecend + "  !=  " + oldWorldToFormula(oldConditional.getAntecedent()));
        if (!newConsequence.equals(oldWorldToFormula(oldConditional.getConsequence())))
            throw new RuntimeException("Translating worlds failed!");

        return new NewConditional(newConsequence, newAntecend, oldConditional.getNumber());
    }


    //this translates a world to a propositional formula
    public AbstractFormula worldToFormula(World world) {

        //if there is a short formula return this
        if (shortTranslationMap.translate(world.getNumber()) != null)
            return shortTranslationMap.translate(world.getNumber());

            //else return the simple translation
        else
            return oldWorldToFormula(world);
    }


    //this translates possible worlds to propositional formulas
    //the returned formulas are conjunctions of the list of possible worlds
    //like this the formulas are not as short as possible but correct
    private AbstractFormula oldWorldToFormula(World world) {
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
                if (formulaToReturn == null)
                    formulaToReturn = new Conjunction(firstAtom, secondAtom, thirdAtom);
                else formulaToReturn = formulaToReturn.or(new Conjunction(firstAtom, secondAtom, thirdAtom));

            }

        }


        return formulaToReturn;
    }
}
