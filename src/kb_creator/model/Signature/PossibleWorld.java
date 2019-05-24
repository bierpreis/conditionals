package kb_creator.model.Signature;


public class PossibleWorld {
    private boolean a;
    private boolean b;
    private boolean c;

    //todo aybe abstract world and one world with 2 and one with 3 elemnts or sth else?
    public PossibleWorld(boolean a, boolean b) {
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

    public String toString() {
        return a + " " + b;
    }


}
