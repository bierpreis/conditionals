package kb_creator.model.PropositionalLogic;

public class Interpretation {

    //todo: this is useless.
    //make maybe enum for signature
    //or some abstract and concrete signature?
    private boolean a;
    private boolean b;
    private boolean c;

    public Interpretation(boolean a, boolean b, boolean c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }


    public boolean isA() {
        return a;
    }

    public boolean isC() {
        return c;
    }

    public boolean isB() {
        return b;
    }
}
