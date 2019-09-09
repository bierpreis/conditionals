package nfc.model;

import com.google.errorprone.annotations.Var;
import kb_creator.model.propositional_logic.*;
import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.HashMap;
import java.util.Map;

public class ShortTranslationMap {
    private Map<Integer, AbstractFormula> translationMap = new HashMap<>();

    public ShortTranslationMap(AbstractSignature signature) {

        
        if (signature instanceof AB) {
            translationMap.put(5, new Atom(Variable.a));
            translationMap.put(6, new Atom(Variable.b));
            translationMap.put(7, new Atom(Variable.a).eq(new Atom(Variable.b)));

            translationMap.put(8, new Atom(Variable.a).eq(new Atom(Variable.b)).neg());

            translationMap.put(9, new Atom(Variable.b).neg());
            translationMap.put(10, new Atom(Variable.a).neg());

            translationMap.put(11, new Atom(Variable.a).neg().and(new Atom(Variable.b).neg()).neg());

            //translationMap.put(12, new Atom(Variable.a).neg().and(new Atom(Variable.b)).neg());
            translationMap.put(12, new Atom(Variable.a).or(new Atom(Variable.b).neg()));

            //translationMap.put(13, new Atom(Variable.a).and(new Atom(Variable.b).neg()).neg());
            translationMap.put(13, new Atom(Variable.a).neg().or(new Atom(Variable.b)));

            translationMap.put(14, new Atom(Variable.a).and(new Atom(Variable.b)).neg());


            translationMap.put(15, new Tautology());


        } else if (signature instanceof ABC) {

        }
    }

    public AbstractFormula translate(int worldsNumber) {
        return translationMap.get(worldsNumber);
    }
}
