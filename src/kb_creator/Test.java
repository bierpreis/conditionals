package kb_creator;

import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.propositional_logic.*;
import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;
import kb_creator.model.propositional_logic.worlds.ABCWorld;
import kb_creator.model.propositional_logic.worlds.ABWorld;
import kb_creator.model.propositional_logic.worlds.AbstractWorld;
import nfc.model.Conditional;
import nfc.model.World;

public class Test {

    public static void main(String[] args) {

        equalsTest();

    }

    private static void signatureTest() {
        AbstractFormula a = new Atom(Variable.a);
        a = a.neg();
        AbstractFormula b = new Atom(Variable.b);
        b = b.neg();
        AbstractFormula c = new Atom(Variable.c);

        ABWorld world = new ABWorld(true, true);

        //AbstractFormula formula = new Tautology();

        AbstractFormula formulaToTest = a.or(b);

        formulaToTest = formulaToTest.neg();

        System.out.println("test: " + formulaToTest.neg().evaluate(world));

        //System.out.println("formula: " + formula.and(a).neg().and(b).and(c).evaluate(world));

    }

    private static void otherTest() {
        World antecend = new World();
        antecend.addInt(1);

        antecend.addInt(2);
        antecend.addInt(3);

        World otherAntecend = new World();
        otherAntecend.addInt(1);
        otherAntecend.addInt(2);
        otherAntecend.addInt(3);


        World consequence = new World();
        consequence.addInt(2);

        Conditional conditional = new Conditional(consequence, antecend);
        Conditional otherConditional = new Conditional(consequence, otherAntecend);


/*        NewConditional newConditional = new NewConditional(conditional);

        NewConditional otherNewConditional = new NewConditional(otherConditional);

        System.out.println(newConditional + "equals: " + otherNewConditional + newConditional.equals(otherNewConditional));
        */

    }

    private static void counterConditionalTest() {
        World antecend = new World();
        antecend.addInt(1);
        antecend.addInt(2);

        World consequence = new World();
        consequence.addInt(2);

/*        Conditional firsConditonal = new Conditional(consequence, antecend);

        NewConditional newFirstConditional = new NewConditional(firsConditonal);

        System.out.println(newFirstConditional);

        System.out.println(newFirstConditional.getCounterConditional());*/
    }

    private static void formulaTest() {
        AbstractFormula a = new Atom(Variable.a);
        AbstractFormula b = new Atom(Variable.b);
        AbstractFormula c = new Atom(Variable.c);

        AbstractFormula notB = new Atom(Variable.b).neg();

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


        AbstractFormula a = new Atom(Variable.a);

        AbstractFormula b = new Atom(Variable.b);

        AbstractFormula first = a.or(b.neg());

        AbstractFormula second = a.and(b).or(a.and(b.neg())).or(a.neg().and(b.neg()));

        System.out.println(first.equals(second));

        System.out.println(a.equals(a.and(b).or(a.and(b.neg()))));

        System.out.println(a.or(b.neg()).equals(a.and(b).or(a.and(b.neg()).or(a.neg().and(b.neg())))));

        System.out.println(b.neg().equals(a.and(b.neg()).or(a.neg().and(b))));
    }
}
