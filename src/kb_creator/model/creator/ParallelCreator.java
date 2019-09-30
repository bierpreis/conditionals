package kb_creator.model.creator;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ParallelCreator extends AbstractCreator {

    //todo: gui should set this
    private int numberOfThreads = 2;


    private BlockingQueue<AbstractPair> inputQueue = new ArrayBlockingQueue<>(500);

    private BlockingQueue<AbstractKnowledgeBase> consistentQueue = new ArrayBlockingQueue<>(500);

    private BlockingQueue<AbstractKnowledgeBase> inConsistentQueue = new ArrayBlockingQueue<>(500);

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private List<CandidateThread> threadList = new ArrayList<>(numberOfThreads);

    //todo
    public ParallelCreator() {
        for (int i = 0; i < numberOfThreads; i++) {
            CandidateThread thread = new CandidateThread(i);
            threadList.add(thread);
            executorService.submit(thread);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("now interrupting!");

        for (CandidateThread thread :threadList)
            thread.i
    }

    @Override
    public void run() {
        //todo
    }


    @Override
    public void stop() {
        super.stop();
        //todo: interrupt single executors?

        executorService.shutdown();
    }

    @Override
    protected void addConsistentKb(AbstractKnowledgeBase knowledgeBase) {
        inConsistentQueue.add(knowledgeBase);
    }

    public static void main(String[] args) {
        new ParallelCreator();
    }


}
