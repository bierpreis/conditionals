package kb_creator;

import kb_creator.model.logic.*;
import kb_creator.model.logic.signature.AB;
import kb_creator.model.logic.signature.ABC;
import kb_creator.model.logic.signature.AbstractSignature;
import kb_creator.model.logic.signature.worlds.ABCWorld;
import kb_creator.model.logic.signature.worlds.ABWorld;
import kb_creator.model.logic.signature.worlds.AbstractWorld;
import nfc_creator.model.WConditional;
import nfc_creator.model.WorldsList;

public class Test {

    public static void main(String[] args) {

        equalsTest();

    }

    private static void signatureTest() {
        AbstractFormula a = new Atom(Var.a);
        a = a.neg();
        AbstractFormula b = new Atom(Var.b);
        b = b.neg();
        AbstractFormula c = new Atom(Var.c);

        ABWorld world = new ABWorld(true, true);

        //AbstractFormula formula = new Tautology();

        AbstractFormula formulaToTest = a.or(b);

        formulaToTest = formulaToTest.neg();

        System.out.println("test: " + formulaToTest.neg().evaluate(world));

        //System.out.println("formula: " + formula.and(a).neg().and(b).and(c).evaluate(world));

    }

    private static void otherTest() {
        WorldsList antecedent = new WorldsList();
        antecedent.addInt(1);

        antecedent.addInt(2);
        antecedent.addInt(3);

        WorldsList otherAntecend = new WorldsList();
        otherAntecend.addInt(1);
        otherAntecend.addInt(2);
        otherAntecend.addInt(3);


        WorldsList consequence = new WorldsList();
        consequence.addInt(2);

        WConditional conditional = new WConditional(consequence, antecedent);
        WConditional otherConditional = new WConditional(consequence, otherAntecend);


/*        NewConditional newConditional = new NewConditional(conditional);

        NewConditional otherNewConditional = new NewConditional(otherConditional);

        System.out.println(newConditional + "equals: " + otherNewConditional + newConditional.equals(otherNewConditional));
        */

    }

    private static void counterConditionalTest() {
        WorldsList antecend = new WorldsList();
        antecend.addInt(1);
        antecend.addInt(2);

        WorldsList consequence = new WorldsList();
        consequence.addInt(2);

/*        Conditional firsConditonal = new Conditional(consequence, antecend);

        NewConditional newFirstConditional = new NewConditional(firsConditonal);

        System.out.println(newFirstConditional);

        System.out.println(newFirstConditional.getCounterConditional());*/
    }

    private static void formulaTest() {
        AbstractFormula a = new Atom(Var.a);
        AbstractFormula b = new Atom(Var.b);
        AbstractFormula c = new Atom(Var.c);

        AbstractFormula notB = new Atom(Var.b).neg();

        AbstractWorld testWorld = new ABCWorld(true, false, true);


        AbstractFormula first = new Conjunction(a, notB, c);
        AbstractSignature abc = new ABC();

        for (AbstractWorld world : abc.getPossibleWorlds())
            System.out.println("first: " + first.evaluate(world) + "(" + world + ")");

        AbstractFormula second = new Conjunction(c, notB, a);

        System.out.println("second: " + second.evaluate(testWorld));

        //System.out.println(first.equals(second));

        AbstractFormula third = first.or(second);

        System.out.println(third.evaluate(testWorld));
    }

    private static void equalsTest() {
        AbstractFormula.setSignature(new AB());


        AbstractFormula a = new Atom(Var.a);

        AbstractFormula b = new Atom(Var.b);

        AbstractFormula first = a.or(b.neg());

        AbstractFormula second = a.and(b).or(a.and(b.neg())).or(a.neg().and(b.neg()));

        System.out.println(first.equals(second));

        System.out.println(a.equals(a.and(b).or(a.and(b.neg()))));

        System.out.println(a.or(b.neg()).equals(a.and(b).or(a.and(b.neg()).or(a.neg().and(b.neg())))));

        System.out.println(b.neg().equals(a.and(b.neg()).or(a.neg().and(b))));
    }
}
