package kb_creator.model.PropositionalLogic;

public class Disjunction extends AbstractFormula {
    private AbstractFormula firstFormula;
    private AbstractFormula secondFormula;


    public Disjunction(AbstractFormula firstFormula, AbstractFormula secondFormula){
        this.firstFormula = firstFormula;
        this.secondFormula = secondFormula;
    }
    @Override
    public boolean evaluate(Interpretation interpretation) {
        return firstFormula.evaluate(interpretation) || secondFormula.evaluate(interpretation);

    }
}
