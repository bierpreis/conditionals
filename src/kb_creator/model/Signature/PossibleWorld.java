package kb_creator.model.Signature;

//todo: this class
public class PossibleWorld {
    private boolean a;
    private boolean b;
    private boolean c;

    //maybe abstract world and one world with 2 and one with 3 elemnts or sth else?
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
