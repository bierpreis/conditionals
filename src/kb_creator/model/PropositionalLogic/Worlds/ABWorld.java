package kb_creator.model.PropositionalLogic.Worlds;


public class ABWorld extends AbstractWorld {
    private boolean a;
    private boolean b;


    //todo aybe abstract world and one world with 2 and one with 3 elemnts or sth else?


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
        return a + " " + b;
    }


}
