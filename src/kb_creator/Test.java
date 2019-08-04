package kb_creator;

import kb_creator.model.propositional_logic.NewConditional;
import kb_creator.model.propositional_logic.*;
import kb_creator.model.propositional_logic.worlds.ABWorld;
import nfc.model.Conditional;
import nfc.model.World;

public class Test {

    public static void main(String[] args) {

        String lol = "lol";
        System.out.println(lol.matches("l.*"));

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

    //todo: test logic here
    private static void formulaTest() {
        AbstractFormula a = new Atom(Variable.a);
        AbstractFormula b = new Atom(Variable.b);
        AbstractFormula c = new Atom(Variable.c);
        c = c.neg();

        AbstractFormula first = new Conjunction(a, b, c);

        AbstractFormula second = new Conjunction(c, b, a);

        System.out.println(first.equals(second));
    }
}
