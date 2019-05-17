package kb_creator.model.Signature;

//todo: this class
public class PossibleWorld {
    private boolean a;
    private boolean b;
    private boolean c;

    public PossibleWorld(boolean a, boolean b, boolean c) {
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
