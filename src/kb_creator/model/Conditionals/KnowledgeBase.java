package kb_creator.model.Conditionals;

import kb_creator.model.PropositionalLogic.AbstractFormula;
import kb_creator.model.PropositionalLogic.Tautology;
import kb_creator.model.PropositionalLogic.Worlds.AbstractWorld;
import kb_creator.model.Signature.AbstractSignature;


import java.util.LinkedList;
import java.util.List;

//todo: a kb with numbers instead of real conditionals to save memory. numbers can be mapped to conditionals by hashmap.
public class KnowledgeBase {
    private AbstractSignature signature;
    private List<NewConditional> conditionalList;
    private int number;

    public KnowledgeBase(AbstractSignature signature, int number) {
        conditionalList = new LinkedList<>();
        this.signature = signature;
        this.number = number;
    }


    //todo: this
    public boolean isConsistent(NewConditional conditionalToTest) {
        //this test is written in goldszmit/pearl 1996 p 64 (tolerance)
        //siehe auch infofc s 4 dazu. auch s 9 dort.


        AbstractFormula concistecyOfKB = new Tautology();

        for (NewConditional conditionalFromList : conditionalList) {
            concistecyOfKB = concistecyOfKB.and(conditionalFromList.getAntecend().neg().or(conditionalFromList.getConsequence()));
        }

        //here sth like:
        for (AbstractWorld world : signature.getPossibleWorlds()) {
            if (conditionalToTest.getAntecend().evaluate(world) && conditionalToTest.getConsequence().evaluate(world) && concistecyOfKB.evaluate(world)) {

//                System.out.println("consistent: " + world.toString());
//                System.out.println(conditionalToTest);
//                System.out.println(concistecyOfKB);
//                System.out.println();

                return true;

            }

        }

//        System.out.println("inconsistent: ");
//        System.out.println(conditionalToTest);
//        System.out.println(concistecyOfKB);
//        System.out.println();

        return false;
    }

    public int getNumber() {
        return number;
    }

    public void add(NewConditional conditional) {
        conditionalList.add(conditional);
    }

    public void add(KnowledgeBase knowledgeBaseToAdd) {
        for (NewConditional conditional : knowledgeBaseToAdd.conditionalList) {
            conditionalList.add(conditional);
        }
    }

    public int getSize() {
        return conditionalList.size();
    }

    @Override
    public String toString() {
        return conditionalList.toString();
    }


    //this is old and unused for performance reasons
    //maybe use it to compare new stringbuilder with this old versions
    public String toFileString() {
        String stringToReturn = "signature\n";
        stringToReturn = stringToReturn + signature.toString().toLowerCase();

        stringToReturn = stringToReturn + "\n";
        stringToReturn = stringToReturn + "conditionals\n\n";
        stringToReturn = stringToReturn + this.number + "{\n";

        //todo: this is shit
        for (int i = 0; i < conditionalList.size(); i++) {
            stringToReturn = stringToReturn + conditionalList.get(i);
            if (i != conditionalList.size() - 1)
                stringToReturn = stringToReturn + ",\n";
        }

        stringToReturn = stringToReturn + "\n}";
        return stringToReturn;
    }

    public String newToFileString() {
        StringBuffer sb = new StringBuffer();
        sb.append(signature.toString().toLowerCase());
        sb.append("\n");
        sb.append("conditionals\n\n");
        sb.append(this.number);
        sb.append("{\n");

        for (int i = 0; i < conditionalList.size(); i++) {
            sb.append(conditionalList.get(i));
            if (i != conditionalList.size() - 1)
                sb.append(",\n");

        }
        sb.append("\n}");
        return sb.toString();

    }

}
