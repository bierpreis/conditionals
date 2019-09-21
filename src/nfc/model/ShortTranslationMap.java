package nfc.model;

import kb_creator.model.propositional_logic.*;
import kb_creator.model.propositional_logic.signature.AbstractSignature;
import java.util.Map;

public class ShortTranslationMap {
    private Map<Integer, AbstractFormula> translationMap;

    public ShortTranslationMap(AbstractSignature signature) {

        FormulaReader formulaReader = new FormulaReader();
        translationMap = formulaReader.getFormulaMapFromFile(signature);

    }
/*        //todo: make ab and abc same
        if (signature instanceof AB) {


*//*            translationMap.put(5, new Atom(Var.a));
            translationMap.put(6, new Atom(Var.b));
            translationMap.put(7, new Atom(Var.a).eq(new Atom(Var.b)));

            translationMap.put(8, new Atom(Var.a).eq(new Atom(Var.b)).neg());

            translationMap.put(9, new Atom(Var.b).neg());
            translationMap.put(10, new Atom(Var.a).neg());

            translationMap.put(11, new Atom(Var.a).neg().and(new Atom(Var.b).neg()).neg());

            //translationMap.put(12, new Atom(Var.a).neg().and(new Atom(Var.b)).neg());
            translationMap.put(12, new Atom(Var.a).or(new Atom(Var.b).neg()));

            //translationMap.put(13, new Atom(Var.a).and(new Atom(Var.b).neg()).neg());
            translationMap.put(13, new Atom(Var.a).neg().or(new Atom(Var.b)));

            translationMap.put(14, new Atom(Var.a).and(new Atom(Var.b)).neg());


            translationMap.put(15, new Tautology());*//*


        } else if (signature instanceof ABC) {
            translationMap.put(9, new Atom(Var.a).and(new Atom(Var.b)));
            translationMap.put(10, new Atom(Var.a).and(new Atom(Var.c)));
            translationMap.put(12, new Atom(Var.b).and(new Atom(Var.c)));

            translationMap.put(15, new Atom(Var.a).eq(new Atom(Var.b)).eq(new Atom(Var.c)));

            translationMap.put(30, new Atom(Var.b).neg().and(new Atom(Var.c).neg()));
            translationMap.put(31, new Atom(Var.a).neg().and(new Atom(Var.b)));
            translationMap.put(32, new Atom(Var.a).neg().and(new Atom(Var.c)));

            translationMap.put(36, new Atom(Var.a).neg().and(new Atom(Var.b).neg()));
            //translationMap.put(38, new Atom(Var.a).and(new Atom(Var.b).neg()));

            translationMap.put(93, new Atom(Var.a));
            translationMap.put(102, new Atom(Var.b));

            translationMap.put(113, new Atom(Var.c));

            translationMap.put(142, new Atom(Var.c).neg());

            translationMap.put(153, new Atom(Var.b).neg());

            translationMap.put(162, new Atom(Var.a).neg());
            translationMap.put(163, new Atom(Var.a).or(new Atom(Var.b).and(new Atom(Var.c))));

            translationMap.put(209, new Atom(Var.a).neg().and(new Atom(Var.b)).or(new Atom(Var.c).neg()));

            translationMap.put(211, new Atom(Var.a).neg().and(new Atom(Var.b).neg()).or(new Atom(Var.c).neg()));
            translationMap.put(212, new Atom(Var.a).neg().or((new Atom(Var.b)).and(new Atom(Var.c).neg())));
            translationMap.put(213, new Atom(Var.a).and(new Atom(Var.b).neg()).or(new Atom(Var.a).neg().and(new Atom(Var.b))).or(new Atom(Var.a).neg().and(new Atom(Var.b).neg().and(new Atom(Var.c)))));
            translationMap.put(214, new Atom(Var.a).and(new Atom(Var.b).neg()).or(new Atom(Var.a).neg().and(new Atom(Var.b))).or(new Atom(Var.a).neg().and(new Atom(Var.b).neg().and(new Atom(Var.c).neg()))));
            translationMap.put(215, new Atom(Var.b).neg().or(new Atom(Var.a).neg().and(new Atom(Var.c))));
            translationMap.put(216, new Atom(Var.b).neg().or(new Atom(Var.a).neg().and(new Atom(Var.c).neg())));
            translationMap.put(217, new Atom(Var.a).neg().or(new Atom(Var.b).neg().and(new Atom(Var.c))));
            translationMap.put(218, new Atom(Var.a).neg().or(new Atom(Var.b).neg().and(new Atom(Var.c).neg())));
            translationMap.put(219, new Atom(Var.a).or(new Atom(Var.a).neg().and(new Atom(Var.b))));
            translationMap.put(220, new Atom(Var.a).or(new Atom(Var.a).neg().and(new Atom(Var.c))));
            translationMap.put(221, new Atom(Var.a).or(new Atom(Var.b).eq(new Atom(Var.c))));
            translationMap.put(223, new Atom(Var.a).or(new Atom(Var.a).neg().and(new Atom(Var.c).neg())));
            translationMap.put(224, new Atom(Var.a).or(new Atom(Var.a).neg().and(new Atom(Var.b).neg())));

            translationMap.put(233, new Atom(Var.a).neg().or(new Atom(Var.a).and(new Atom(Var.b))));

            translationMap.put(236, new Atom(Var.b).neg().or(new Atom(Var.c)));
            translationMap.put(237, new Atom(Var.b).neg().or(new Atom(Var.a).eq(new Atom(Var.c))));

            translationMap.put(239, new Atom(Var.a).neg().or(new Atom(Var.b).eq(new Atom(Var.c))));

            translationMap.put(244, new Atom(Var.a).neg().or(new Atom(Var.a).and(new Atom(Var.b).eq(new Atom(Var.c))).neg()));
            translationMap.put(245, new Atom(Var.a).neg().or(new Atom(Var.a).and(new Atom(Var.c).neg())));
            translationMap.put(246, new Atom(Var.a).neg().or(new Atom(Var.a).and(new Atom(Var.b).neg())));
            translationMap.put(247, new Atom(Var.a).neg().and(new Atom(Var.b).neg()).and(new Atom(Var.c).neg()).neg());
            translationMap.put(248, new Atom(Var.a).neg().and(new Atom(Var.b).neg()).and(new Atom(Var.c)).neg());
            translationMap.put(249, new Atom(Var.a).neg().and(new Atom(Var.b)).and(new Atom(Var.c).neg()).neg());
            translationMap.put(250, new Atom(Var.a).neg().and(new Atom(Var.b)).and(new Atom(Var.c)).neg());
            translationMap.put(251, new Atom(Var.a).and(new Atom(Var.b).neg()).and(new Atom(Var.c).neg()).neg());
            translationMap.put(252, new Atom(Var.a).and(new Atom(Var.b).neg()).and(new Atom(Var.c)).neg());
            translationMap.put(253, new Atom(Var.a).and(new Atom(Var.b)).and(new Atom(Var.c).neg()).neg());
            translationMap.put(254, new Atom(Var.a).and(new Atom(Var.b)).and(new Atom(Var.c)).neg());
            translationMap.put(255, new Tautology());
        }
    */

    public AbstractFormula translate(int worldsNumber) {
        return translationMap.get(worldsNumber);
    }
}
