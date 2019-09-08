package nfc.model;

import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortTranslationMap {
    private Map<List<Integer>, AbstractFormula> translationMap = new HashMap<>();

    public ShortTranslationMap(AbstractSignature signature) {
        //todo: add short translations here

        if (signature instanceof AB) {

        } else if (signature instanceof ABC) {
                
        }
    }

    public AbstractFormula translate(List<Integer> woldsList) {
        return translationMap.get(woldsList);
    }
}
