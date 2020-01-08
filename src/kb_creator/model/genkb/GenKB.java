package kb_creator.model.genkb;

import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.pairs.RealPair;
import kb_creator.model.logic.AbstractFormula;
import kb_creator.model.logic.KnowledgeBase;
import kb_creator.model.logic.PConditional;

import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.logic.signature.AbstractSignature;
import kb_creator.model.writer.AbstractKbWriter;
import kb_creator.model.writer.KbWriterOptions;
import kb_creator.model.writer.WriterFactory;
import nfc_creator.model.NfcCreator;


import java.util.*;
import java.util.concurrent.BlockingQueue;

public class GenKB implements Runnable {

    private long currentPairAmount;

    private volatile GenKbStatus genKbStatus;

    protected AbstractSignature signature;

    protected int k;

    private long startTime;

    private List<PConditional> nfc;

    private List<PConditional> cnfc;


    private AbstractKbWriter kbWriter;

    private BlockingQueue<KnowledgeBase> inconsistentWriterQueue;

    private BlockingQueue<AbstractPair> newIterationQueue;
    private BlockingQueue<AbstractPair> lastIterationQueue;

    private AbstractPairBuffer l;

    private long iterationPairCounter = 0;


    public GenKB(AbstractSignature signature, AbstractPairBuffer l, KbWriterOptions writerOptions) {
        System.out.println("new simple creator");
        this.newIterationQueue = l.getNextIterationQueue();
        this.lastIterationQueue = l.getLastIterationQueue();

        this.l = l;
        AbstractFormula.setSignature(signature);
        KnowledgeBase.setSignature(signature);
        PConditional.setSignature(signature);


        genKbStatus = GenKbStatus.NOT_STARTED;
        this.signature = signature;

        kbWriter = WriterFactory.getKbWriter(writerOptions);

        l.setConsistentQueue(kbWriter.getConsistentQueue());

        inconsistentWriterQueue = kbWriter.getInconsistentQueue();

        genKbStatus = GenKbStatus.CREATING_CONDITIONALS;

        NfcCreator nfcCreator = new NfcCreator(signature);

        nfc = Collections.unmodifiableList(nfcCreator.getpNfc());
        cnfc = Collections.unmodifiableList(nfcCreator.getpCnfc());

        AbstractPair.setNfc(nfcCreator.getNfcMap());
        KnowledgeBase.setNfcMap(nfcCreator.getNfcMap());

        currentPairAmount = 0;

        startTime = System.currentTimeMillis();

    }


    private List<AbstractPair> initOneElementKBs(Collection<PConditional> nfc, Collection<PConditional> cnfc) {
        System.out.println("creating 1 element kbs");

        List<AbstractPair> listToReturn = new ArrayList<>(cnfc.size());
        long numberCounter = 1;

        //line 3
        for (PConditional r : cnfc) {

            //line 4 and 5
            KnowledgeBase rKB = new KnowledgeBase(numberCounter, r);
            numberCounter++;

            //create candidates
            List<PConditional> candidatesList = new ArrayList<>();

            for (PConditional conditional : nfc) {
                if (!(conditional.getEqConditionalsList().get(0).getNumber() < r.getNumber()))
                    if (!(conditional.equals(r)))
                        if (!(conditional.equals(r.getCounterConditional())))
                            candidatesList.add(conditional);

            }


            //no buffering for first iteration because it almost makes no difference
            listToReturn.add(new RealPair(rKB, candidatesList));
        }

        System.out.println("finished 1 element kbs");
        return listToReturn;
    }

    @Override
    public void run() {
        try {
            generateKbs();
        } catch (InterruptedException e) {
            System.out.println("GenKb interrupted and finished");
        }
    }


    public void generateKbs() throws InterruptedException {

        genKbStatus = GenKbStatus.RUNNING;
        System.out.println("creator thread started");

        //line 1
        kbWriter.prepareIteration(0);
        l.prepareIteration(0);

        //line 2
        k = 1;


        long numberCounter = 1;

        //line 3
        for (PConditional r : cnfc) {

            //line 4 and 5
            KnowledgeBase rKB = new KnowledgeBase(numberCounter, r);
            numberCounter++;
            List<PConditional> candidatesList = new ArrayList<>();
            for (PConditional conditional : nfc) {
                if (!(conditional.getEqConditionalsList().get(0).getNumber() < r.getNumber()))
                    if (!(conditional.equals(r)))
                        if (!(conditional.equals(r.getCounterConditional())))
                            candidatesList.add(conditional);

            }
            newIterationQueue.put(new RealPair(rKB, candidatesList));
        }

        //one method
        System.out.println("finished 1 element kbs");
        l.finishIteration(0);
        kbWriter.finishIteration();

        //line 6
        while (l.hasElementsForIteration(k)) {
            long startTime = System.currentTimeMillis();

            //line  7
            l.prepareIteration(k);
            currentPairAmount = kbWriter.getIterationConsistentCounter();


            kbWriter.prepareIteration(k);
            iterationPairCounter = 0;

            //counters for numbering the knowledge bases
            long consistentKbCounter = 1;
            long inconsistentKbCounter = 1;

            //line 8
            while (l.hasMoreElementsForK(k)) {
                AbstractPair currentPair = lastIterationQueue.take();
                iterationPairCounter++;


                //line 9
                for (PConditional r : currentPair.getCandidatesList()) {


                    //line 10
                    if (currentPair.getKnowledgeBase().isConsistentWith(r)) {


                        //next part is line 11 and 12
                        KnowledgeBase knowledgeBaseToAdd = new KnowledgeBase(consistentKbCounter, currentPair.getKnowledgeBase(), r);
                        consistentKbCounter++;
                        List<PConditional> candidatesToAdd = new ArrayList<>();
                        for (PConditional candidate : currentPair.getCandidatesList())
                            if (candidate.getNumber() > r.getNumber() && !candidate.equals(r.getCounterConditional()))
                                candidatesToAdd.add(candidate);


                        //line 12
                        newIterationQueue.put(new RealPair(knowledgeBaseToAdd, candidatesToAdd));


                    } else {
                        inconsistentWriterQueue.put(new KnowledgeBase(inconsistentKbCounter, currentPair.getKnowledgeBase(), r));
                        inconsistentKbCounter++; //counter is only for kb constructor
                    }
                }

                currentPair.clear(); //saves a lot of memory and takes almost no time

            }
            System.out.println("time for iteration " + k + ": " + (System.currentTimeMillis() - startTime) / 1000 + "s");


            //line 13
            l.finishIteration(k);
            kbWriter.finishIteration();
            k = k + 1;
        }
        genKbStatus = GenKbStatus.FINISHED;
        finishAndStopLoop();


    }


    //this is only called by gui stop button
    public void stopLoop() {
        kbWriter.stopThreads();
        this.genKbStatus = GenKbStatus.STOPPED;
    }

    //this is only called when all iterations have finished
    private void finishAndStopLoop() {
        System.out.println("genkb finished");
        kbWriter.finishAndStopThreads();
        this.genKbStatus = GenKbStatus.FINISHED;
    }


    //getters

    public AbstractPairBuffer getPairBuffer() {
        return l;
    }

    public AbstractKbWriter getKbWriterThread() {
        return kbWriter;
    }

    public int getCurrentK() {
        return k;
    }

    public long getTotalKbAmount() {
        return kbWriter.getTotalConsistentCounter();
    }


    public GenKbStatus getCreatorStatus() {
        return genKbStatus;
    }

    public long getCurrentPairAmount() {
        return currentPairAmount;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getTotalInconsistentAmount() {
        return kbWriter.getTotalInconsistentCounter();
    }

    public float calculateProgress() {

        //avoid division with zero
        if (currentPairAmount == 0) {
            return 0;

        }
        return (iterationPairCounter / (float) currentPairAmount) * 100;
    }


}




