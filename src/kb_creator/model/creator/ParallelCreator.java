package kb_creator.model.creator;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

public class ParallelCreator extends AbstractCreator {


    @Override
    public void run() {
        //todo
    }


    @Override
    public void stop() {
        super.stop();
        //todo: there should be more to stop
    }

    @Override
    protected void addConsistentKb(AbstractKnowledgeBase knowledgeBase) {
        //todo: add to queue
    }


}
