package nfc.model;

import kb_creator.model.propositional_logic.AbstractFormula;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortTranslationMap {
    private Map<List<Integer>, AbstractFormula> translationMap = new HashMap<>();

    public ShortTranslationMap() {
        //todo: add short translations here
        //needs to be 1 map for ab and one for abc
    }

    public AbstractFormula translate(List<Integer> woldsList) {
        return translationMap.get(woldsList);
    }
}
