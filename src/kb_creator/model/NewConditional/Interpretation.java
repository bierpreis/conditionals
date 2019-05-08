package kb_creator.model.NewConditional;

public class Interpretation {
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
