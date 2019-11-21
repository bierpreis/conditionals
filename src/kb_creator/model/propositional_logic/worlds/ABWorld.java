package kb_creator.model.propositional_logic.worlds;


import kb_creator.model.propositional_logic.Var;

public class ABWorld extends AbstractWorld {
    private boolean a;
    private boolean b;

    public ABWorld(boolean a, boolean b) {
        this.a = a;
        this.b = b;

    }


    public String toString() {
        return a + "," + b;
    }

    @Override
    public boolean get(Var var) {
        if (var.equals(Var.a))
            return a;
        if (var.equals(Var.b))
            return b;
        else throw new RuntimeException("Invalid variable in AB World: " + var);
    }


}
