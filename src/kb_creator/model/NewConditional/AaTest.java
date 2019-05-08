package kb_creator.model.NewConditional;

import cucumber.api.java.ca.I;

public class AaTest {

    public static void main(String[] args) {
        Interpretation interpretation = new Interpretation(true, true, true);

        AbstractFormula a = new Atom(Variable.a);
        a = new Negation(a);

        System.out.println(a.evaluate(interpretation));
    }
}
