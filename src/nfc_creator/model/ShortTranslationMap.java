package nfc_creator.model;

import kb_creator.model.logic.*;
import kb_creator.model.logic.signature.AbstractSignature;

import java.util.Map;

public class ShortTranslationMap {
    private Map<Integer, AbstractFormula> translationMap;

    //this class is muted
    //because it causes problems in the jar file
    //because it cant read correctly the text files in resources
    public ShortTranslationMap(AbstractSignature signature) {

/*        FormulaReader formulaReader = new FormulaReader();
        translationMap = formulaReader.getFormulaMapFromFile(signature);*/

    }

    //short translations don't speed up anything. so why they are muted.
    //problem with short formulas: printing conditionals gets out short formulas not normal form!
    public AbstractFormula translate(int worldsNumber) {
        //return null to test what happens if nothing gets translated
        return null;
        //return translationMap.get(worldsNumber);
    }
}
