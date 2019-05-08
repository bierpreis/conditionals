package kb_creator.model.NewConditional;

public class AaTest {

    public static void main(String[] args) {
        Interpretation interpretation = new Interpretation(true, true, true);

        AbstractFormula a = new Atom(Variable.a);
        //a = a.neg();

        AbstractFormula b = new Atom(Variable.b);

        AbstractFormula conjunction = a.or(b);


        System.out.println(conjunction.evaluate(interpretation));
    }
}
