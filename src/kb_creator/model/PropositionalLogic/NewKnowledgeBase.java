package kb_creator.model.PropositionalLogic;

import nfc.model.Conditional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NewKnowledgeBase {
    private List<NewConditional> conditionalList;

    public NewKnowledgeBase() {
        conditionalList = new LinkedList<>();
    }

    public boolean isConsistent(NewConditional conditional) {
        //todo this test is written in goldszmit/pearl 1996 p 65
        //siehe auch infofc s 4 dazu. auch s 9 dort.


        //this sleep is placeholder. remove when implement sth useful here
        //sleep(1);
        return true;
    }

    public void add(NewConditional conditional) {
        conditionalList.add(conditional);
    }

    public void add(NewKnowledgeBase knowledgeBaseToAdd) {
        for (NewConditional conditional : knowledgeBaseToAdd.conditionalList) {
            conditionalList.add(conditional);
        }
    }

    public List<NewConditional> getConditionalList() {
        return conditionalList;
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;
    }

    @Override
    public String toString() {
        return conditionalList.toString() + "\n";
    }

}
