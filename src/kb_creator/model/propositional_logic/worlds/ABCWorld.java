package kb_creator.model.propositional_logic.worlds;

import kb_creator.model.propositional_logic.Var;

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

    @Override
    public boolean get(Var var) {
        if (var.equals(Var.a))
            return a;
        if (var.equals(Var.b))
            return b;
        if (var.equals(Var.c))
            return c;
        else throw new RuntimeException("Invalid variable in AB World: " + var);
    }

}
