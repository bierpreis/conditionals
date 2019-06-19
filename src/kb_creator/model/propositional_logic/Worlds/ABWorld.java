package kb_creator.model.propositional_logic.Worlds;


public class ABWorld extends AbstractWorld {
    private boolean a;
    private boolean b;

    public ABWorld(boolean a, boolean b) {
        this.a = a;
        this.b = b;

    }

    public boolean isA() {
        return a;
    }

    public boolean isB() {
        return b;
    }

    public String toString() {
        return "a,b";
    }


}
