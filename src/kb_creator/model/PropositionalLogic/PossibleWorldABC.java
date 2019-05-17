package kb_creator.model.PropositionalLogic;
//todo: rename and do sth with other interpretation named parameters in project
public class PossibleWorldABC {

    //todo: this . combine with some signature. maybe abstract interpretation, one for ab and one for abc?
    //evaluate method should be here and not in abstractformula. maybe signature as abstract formula and then ab and abc concrete?
    private boolean a;
    private boolean b;
    private boolean c;

    public PossibleWorldABC(boolean a, boolean b, boolean c) {
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
