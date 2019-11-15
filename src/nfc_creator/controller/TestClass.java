package nfc_creator.controller;


import kb_creator.model.propositional_logic.signature.AB;
import nfc_creator.model.NfcCreator;
import nfc_creator.model.WorldsList;

public class TestClass {
    public static void main(String[] args) {
        WorldsList.setView(WorldsList.View.LETTERS);
        NfcCreator creator = new NfcCreator(new AB());
        for (WorldsList world : creator.getWorldsList())
            System.out.println(world);
    }


}
