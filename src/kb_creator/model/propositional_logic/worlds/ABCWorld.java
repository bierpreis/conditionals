package kb_creator.model.propositional_logic.worlds;

public class ABCWorld extends AbstractWorld {
    private boolean a, b, c;

    public ABCWorld(boolean a, boolean b, boolean c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return a + "," + b + "," + c;
    }

    public boolean isA() {
        return a;
    }

    public boolean isB() {
        return b;
    }

    public boolean isC() {
        return c;
    }

}
