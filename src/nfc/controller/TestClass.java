package nfc.controller;


import kb_creator.model.Signature.AB;
import nfc.model.NfcCreator;
import nfc.model.World;

public class TestClass {
    public static void main(String[] args) {
        World.setView(World.View.LETTERS);
        NfcCreator creator = new NfcCreator(new AB());
        for (World world : creator.getWorlds())
            System.out.println(world);
    }


}
