package nfc.controller;

import kb_creator.model.PropositionalLogic.AbstractFormula;
import kb_creator.model.PropositionalLogic.Atom;
import kb_creator.model.PropositionalLogic.Variable;
import nfc.model.Conditional;
import nfc.model.World;

public class TestClass {
    public static void main(String[] args) {

        AbstractFormula a = new Atom(Variable.a);
        AbstractFormula notA = new Atom(Variable.a).neg();

        System.out.println("a: " + a);
        System.out.println("b: " + notA);
    }


}
