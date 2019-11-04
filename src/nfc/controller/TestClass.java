package nfc.controller;


import kb_creator.model.propositional_logic.signature.AB;
import nfc.model.NfcCreator;
import nfc.model.WorldSet;

public class TestClass {
    public static void main(String[] args) {
        WorldSet.setView(WorldSet.View.LETTERS);
        NfcCreator creator = new NfcCreator(new AB());
        for (WorldSet world : creator.getWorldList())
            System.out.println(world);
    }


}
