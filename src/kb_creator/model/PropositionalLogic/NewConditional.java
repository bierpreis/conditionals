package kb_creator.model.PropositionalLogic;

import com.google.errorprone.annotations.Var;
import nfc.model.Conditional;
import nfc.model.World;

import java.util.List;

public class NewConditional {
    private AbstractFormula antecend;
    private AbstractFormula consequent;

    public NewConditional(AbstractFormula antecend, AbstractFormula consequent) {
        this.antecend = antecend;
        this.consequent = consequent;
    }

    public NewConditional(Conditional oldConditional) {
        World oldAntecend = oldConditional.getAntecedent();
        World oldConsequence = oldConditional.getConsequence();

    }


    private AbstractFormula worldToFormula(World world) {
        AbstractFormula disJunctionToReturn = null;
        for (int worldInt : world.getWorldsList()) {
            AbstractFormula conjunction = null;
            AbstractFormula firstAtom = new Atom(Variable.a);
            AbstractFormula secondAtom = new Atom(Variable.b);
            switch (worldInt) {


                case 0:

                    firstAtom.neg();
                    secondAtom.neg();

                    break;
                case 1:

                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    throw new RuntimeException("Unknown World: " + worldInt);

            }
            if (disJunctionToReturn == null)
                disJunctionToReturn = new Conjunction(firstAtom, secondAtom);
            else disJunctionToReturn = disJunctionToReturn.and(conjunction);

        }
        return disJunctionToReturn;
    }
}