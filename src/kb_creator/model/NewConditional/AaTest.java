package kb_creator.model.NewConditional;

public class AaTest {

    public static void main(String[] args) {
        AbstractFormula nice = new Atom(Variable.a);
        nice = new Negation(nice);

        System.out.println(nice.toString());
    }
}
