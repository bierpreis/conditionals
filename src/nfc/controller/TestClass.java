package nfc.controller;


import kb_creator.model.propositional_logic.signature.AB;
import nfc.model.NfcCreator;
import nfc.model.WorldList;

public class TestClass {
    public static void main(String[] args) {
        WorldList.setView(WorldList.View.LETTERS);
        NfcCreator creator = new NfcCreator(new AB());
        for (WorldList world : creator.getWorldList())
            System.out.println(world);
    }


}
