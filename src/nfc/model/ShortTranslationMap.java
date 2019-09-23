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

    public AbstractFormula translate(int worldsNumber) {
        return translationMap.get(worldsNumber);
    }
}
