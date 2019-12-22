package nfc_creator.model;

import kb_creator.model.logic.*;
import kb_creator.model.logic.signature.AB;
import kb_creator.model.logic.signature.ABC;
import kb_creator.model.logic.signature.AbstractSignature;

public class ConditionalTranslator {
    private static ShortTranslationMap shortTranslationMap;

    public static void init(AbstractSignature signature) {
        AbstractFormula.setSignature(signature);
        shortTranslationMap = new ShortTranslationMap(signature);
    }

    //todo: describe when finished
    public static PConditional transLate(WConditional wConditional) {
        AbstractFormula normalAntecedent = worldToNormalFormula(wConditional.getAntecedent());
        AbstractFormula shortAntecedent = worldToNormalFormula(wConditional.getAntecedent());

        AbstractFormula normalConsequence = worldToNormalFormula(wConditional.getConsequence());
        AbstractFormula shortConsequence = worldToNormalFormula(wConditional.getConsequence());

        PConditional pConditional = new PConditional(normalConsequence, shortConsequence, normalAntecedent, shortAntecedent, wConditional.getNumber());


        //this is a test that translation of short formulas really worked
        if (!normalAntecedent.equals(shortAntecedent))
            throw new RuntimeException("Translating Worlds failed! " + normalAntecedent + "  !=  " + worldToNormalFormula(wConditional.getAntecedent()) + " (line " + wConditional.getAntecedent().getNumber() + ")");
        if (!normalConsequence.equals(shortConsequence))
            throw new RuntimeException("Translating worlds failed! + " + normalConsequence + "  !=  " + worldToNormalFormula(wConditional.getConsequence()) + " (line" + wConditional.getConsequence().getNumber() + ")");


        return pConditional;
    }


    //this tries to get a short formula and when none is available it returns a normal formula
    public static AbstractFormula tryToShortFormula(WorldsList world) {

        //if there is a short formula return this
        if (shortTranslationMap.translate(world.getNumber()) != null)
            return shortTranslationMap.translate(world.getNumber());

            //else return the normal translation
        else
            return worldToNormalFormula(world);
    }


    //this translates possible worlds to propositional formulas
    //the returned formulas are conjunctions of the list of possible worlds
    //like this the formulas are not as short as possible but correct
    private static AbstractFormula worldToNormalFormula(WorldsList world) {
        AbstractFormula formulaToReturn = null;

        if (world.getSignature() instanceof AB) {

            for (int worldInt : world.getWorldsList()) {
                AbstractFormula firstAtom = new Atom(Var.a);
                AbstractFormula secondAtom = new Atom(Var.b);
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
                AbstractFormula firstAtom = new Atom(Var.a);
                AbstractFormula secondAtom = new Atom(Var.b);
                AbstractFormula thirdAtom = new Atom(Var.c);

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
