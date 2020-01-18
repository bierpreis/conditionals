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

    private BlockingQueue<KnowledgeBase> inconsistentQueue;

    private BlockingQueue<AbstractPair> newIterationQueue;
    private BlockingQueue<AbstractPair> lastIterationQueue;

    private AbstractPairBuffer l;

    private long iterationPairCounter = 0;

    private long iterationStartTime;


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

        inconsistentQueue = kbWriter.getInconsistentQueue();

        genKbStatus = GenKbStatus.CREATING_CONDITIONALS;

        NfcCreator nfcCreator = new NfcCreator(signature);

        nfc = Collections.unmodifiableList(nfcCreator.getpNfc());
        cnfc = Collections.unmodifiableList(nfcCreator.getpCnfc());

        AbstractPair.setNfc(nfcCreator.getNfcMap());
        KnowledgeBase.setNfcMap(nfcCreator.getNfcMap());

        currentPairAmount = 0;

        startTime = System.currentTimeMillis();

    }

    @Override
    public void run() {
        genKbStatus = GenKbStatus.RUNNING;
        System.out.println("creator thread started");
        try {
            generateKbs();
        } catch (InterruptedException e) {
            System.out.println("GenKb interrupted and finished");
        }
    }


    private void generateKbs() throws InterruptedException {
        //line 1
        startIteration(0);


        //line 2
        k = 1;
        long consistentKbCounter = 1;
        long inconsistentKbCounter = 1;


        //line 3
        for (PConditional r : cnfc) {


            //line 4 and 5
            KnowledgeBase rKB = new KnowledgeBase(consistentKbCounter, r);
            consistentKbCounter++;
            List<PConditional> candidatesList = new ArrayList<>();
            for (PConditional conditional : nfc) {
                if (!(conditional.getEqConditionalsList().get(0).getNumber() < r.getNumber()))
                    if (!(conditional.equals(r)))
                        if (!(conditional.equals(r.getCounterConditional())))
                            candidatesList.add(conditional);
            }
            newIterationQueue.put(new RealPair(rKB, candidatesList));
        }
        finishIteration(0);


        //line 6
        while (l.hasElementsForIteration(k)) {


            //line  7
            startIteration(k);
            //counters for numbering the knowledge bases
            consistentKbCounter = 1;
            inconsistentKbCounter = 1;


            //line 8
            while (l.hasMoreElementsForK(k)) {
                AbstractPair currentPair = lastIterationQueue.take();
                iterationPairCounter++;


                //line 9
                for (PConditional r : currentPair.getCandidatesList()) {


                    //line 10
                    if (currentPair.getKnowledgeBase().isConsistentWith(r)) {


                        //line 11 and 12
                        KnowledgeBase knowledgeBaseToAdd = new KnowledgeBase(consistentKbCounter, currentPair.getKnowledgeBase(), r);
                        consistentKbCounter++;
                        List<PConditional> candidatesToAdd = new ArrayList<>();
                        for (PConditional candidate : currentPair.getCandidatesList())
                            if (candidate.getNumber() > r.getNumber() && !candidate.equals(r.getCounterConditional()))
                                candidatesToAdd.add(candidate);


                        //line 12
                        newIterationQueue.put(new RealPair(knowledgeBaseToAdd, candidatesToAdd));


                    } else {
                        inconsistentQueue.put(new KnowledgeBase(inconsistentKbCounter, currentPair.getKnowledgeBase(), r));
                        inconsistentKbCounter++; //counter is only for kb constructor
                    }
                }
                currentPair.clear(); //saves a lot of memory and takes almost no time
            }


            //line 13
            finishIteration(k);
            k = k + 1;
        }
        finishAndStopLoop();
    }

    private void startIteration(int k) {
        iterationStartTime = System.currentTimeMillis();
        System.out.println("start iteration for k: " + k);


        l.prepareIteration(k);
        currentPairAmount = kbWriter.getIterationConsistentCounter();


        kbWriter.prepareIteration(k);
        iterationPairCounter = 0;


    }

    private void finishIteration(int k) {
        l.finishIteration(k);
        kbWriter.finishIteration();
        System.out.println("finished iteration for k: " + k + " in " + (System.currentTimeMillis() - iterationStartTime) / 1000 + "s");
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




