package nfc.controller;

import nfc.model.Conditional;
import nfc.model.World;

public class TestClass {
    public static void main(String[] args) {

        NfcCreatorObserver observer = new NfcCreatorObserver();

        World left = new World();
        left.addInt(1);
        left.addInt(2);

        World right = new World();
        right.addInt(1);
        right.addInt(2);
        right.addInt(3);

        Conditional conditonal = new Conditional(left, right);

        System.out.println(conditonal);

        System.out.println(conditonal.getCounterConditional());
    }


}
