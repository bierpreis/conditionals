package kb_creator.model.creator;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelCreator extends AbstractCreator {

    //todo: gui should set this
    private int numberOfThreads = 2;


    private BlockingQueue<AbstractKnowledgeBase> consistentQueue = new ArrayBlockingQueue<>(500);
    private BlockingQueue<AbstractKnowledgeBase> inConsistentQueue = new ArrayBlockingQueue<>(500);

    private BlockingQueue<AbstractPair> inputPairsQueue = new ArrayBlockingQueue<>(500);
    private BlockingQueue<AbstractPair> outputPairsQueue = new ArrayBlockingQueue<>(500);

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private List<CandidateThread> threadList = new ArrayList<>(numberOfThreads);



    public ParallelCreator(AbstractSignature signature, String kbFilePath) {
        super(signature, kbFilePath);
        System.out.println("new parallel creator");

    }

    @Override
    public void run() {
        creatorStatus = CreatorStatus.RUNNING;
        System.out.println("creator thread started");
        l.prepareIteration(0);

        //line 2
        k = 1;

        //this is actually iteration 0
        //and line 3-5
        l.addNewList(initOneElementKBs(nfc, cnfc));

        //k - 1 because actually the init list is iteration 0
        l.finishIteration(0);

        //line 6
        while (l.hasElementsForK(k)) {

            System.gc();

            //todo: start creator threadsthreads
            l.prepareIteration(k);

            int iterationPairCounter = 0;
            lastIterationAmount = nextCandidatePairAmount;
            nextCandidatePairAmount = 0;
            iterationNumberOfKBs = 0;

            //line  7
            l.addNewList(new ArrayList<>());

            //this is line 8
            while (l.hasMoreElements(k)) {

                progress = calculateProgress(iterationPairCounter, lastIterationAmount);

                if (inputPairsQueue.remainingCapacity() > 0)
                    try {
                        inputPairsQueue.put(l.getNextPair(k));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                iterationPairCounter++;

                //todo: deal with inconsistent and consistent queues and counters and set numbers to consistent queues
                //maybe put inconsistent and consistent counters in kb writer?!

/*                nextCandidatePairAmount++;
                iterationNumberOfKBs++;
                totalNumberOfKBs++;*/

                //this both takes almost no time
                checkIfWaitForWriter();
                if (creatorStatus.equals(CreatorStatus.STOPPED))
                    return;


            }
            //todo: wait until inputqueue is empty, then shutdown threads. wait until they finish

            l.finishIteration(k);
            k = k + 1;
        }
        //todo: close threads
        l.setFinished();
        creatorStatus = CreatorStatus.FINISHED;
    }

    private void startThreads() {
        //todo
        for (int i = 0; i < numberOfThreads; i++) {

            CandidateThread thread = new CandidateThread(i, consistentQueue, inConsistentQueue, inputPairsQueue, outputPairsQueue);
            threadList.add(thread);
            executorService.submit(thread);
        }
    }

    private void waitAndStopThreads() {

        for (CandidateThread thread : threadList)
            thread.askToStop();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //todo: interrupt
        for (CandidateThread thread : threadList)
            thread.
    }


    @Override
    public void stop() {
        super.stop();
        waitAndStopThreads();
    }

    @Override
    protected void addConsistentKb(AbstractKnowledgeBase knowledgeBase) {
        inConsistentQueue.add(knowledgeBase);
    }


}
