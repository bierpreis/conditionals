package kb_creator.model;

import nfc.model.Conditional;

import java.util.List;

public class KnowledgeBase {
    private List<Conditional> conditionalList;

    public KnowledgeBase() {
    }

    public boolean checkConsistency(Conditional conditional) {
        //todo: here concistency test
        //todo this test is written in goldszmit/pearl 1996 p 65
        //siehe auch infofc s 4 dazu. auch s 9 dort.


        //this sleep is placeholder. remove when implement sth useful here
        sleep(1);
        return true;
    }

    public void add(Conditional conditional) {
        conditionalList.add(conditional);
        //todo: sort list?
    }

    public List<Conditional> getConditionalList() {
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
}
