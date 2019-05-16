package kb_creator.model.PropositionalLogic;

public class Interpretation {

    //todo: this . combine with some signature. maybe abstract interpretation, one for ab and one for abc?
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
