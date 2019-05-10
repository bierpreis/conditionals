package nfc.controller;


import nfc.model.NfcCreator;
import nfc.model.World;

public class TestClass {
    public static void main(String[] args) {
        World.setView("letters");
        //todo: fix this problem in letters view
        NfcCreator creator = new NfcCreator("ab");
        for (World world : creator.getWorlds())
            System.out.println(world);
    }


}
