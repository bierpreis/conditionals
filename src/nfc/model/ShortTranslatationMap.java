package nfc.model;

import kb_creator.model.propositional_logic.AbstractFormula;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortTranslatationMap {
    private Map<List<Integer>, AbstractFormula> translationMap = new HashMap<>();

    public ShortTranslatationMap() {
        //todo: add short translations here
    }

    public AbstractFormula translate(List<Integer> woldsList) {
        return translationMap.get(woldsList);
    }
}
