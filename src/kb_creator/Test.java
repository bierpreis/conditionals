package kb_creator;

import kb_creator.model.Conditionals.NewConditional;
import kb_creator.model.PropositionalLogic.*;
import kb_creator.model.PropositionalLogic.Worlds.ABWorld;
import nfc.model.Conditional;
import nfc.model.World;

public class Test {

    public static void main(String[] args) {
        counterConditionalTest();

    }

    private static void signatureTest() {
        AbstractFormula a = new Atom(Variable.a);
        a = a.neg();
        AbstractFormula b = new Atom(Variable.b);
        AbstractFormula c = new Atom(Variable.c);

        ABWorld world = new ABWorld(true, true);

        AbstractFormula formula = new Tautology();

        System.out.println("formula: " + formula.and(a).and(b).and(c).evaluate(world));

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


        NewConditional newConditional = new NewConditional(conditional);

        NewConditional otherNewConditional = new NewConditional(otherConditional);

        System.out.println(newConditional + "equals: " + otherNewConditional + newConditional.equals(otherNewConditional));
    }

    private static void counterConditionalTest() {
        World antecend = new World();
        antecend.addInt(1);
        antecend.addInt(2);

        World consequence = new World();
        consequence.addInt(2);

        Conditional firsConditonal = new Conditional(consequence, antecend);

        NewConditional newFirstConditional = new NewConditional(firsConditonal);

        System.out.println(newFirstConditional);

        System.out.println(newFirstConditional.getCounterConditional());
    }
}
