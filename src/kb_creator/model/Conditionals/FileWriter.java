package kb_creator.model.Conditionals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


public class FileWriter {
    //todo: maybel delete
    private Map<Integer, NewConditional> nfcMap;

    private String consistentKbFolder;
    private String inconsistentKbFolder;
    private String candidatePairFolder;

    public FileWriter(String filePathToSave, int kbAmount) {

        //if filepath is null nothing should be saved so nothing should happen here
        if (filePathToSave != null) {
            filePathToSave = filePathToSave + "/" + kbAmount;


            consistentKbFolder = filePathToSave + "/Consistent/";
            inconsistentKbFolder = filePathToSave + "/Inconsistent/";
            candidatePairFolder = filePathToSave + "/CandidatePair/";

            //create folders
            new File(consistentKbFolder);
            new File(inconsistentKbFolder);
            new File(candidatePairFolder);


        }
    }

    public void writeConsistentKBToFile(KnowledgeBase knowledgeBase) {

        if (consistentKbFolder != null)
            try {
                File dir = new File(consistentKbFolder + knowledgeBase.getKbNumber() + ".txt");
                dir.mkdirs();

                PrintWriter writer = new PrintWriter(consistentKbFolder + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.newToFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    public void writeInconsistentKBToFile(KnowledgeBase knowledgeBase) {

        if (inconsistentKbFolder != null)
            try {
                PrintWriter writer = new PrintWriter(inconsistentKbFolder + "/" + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.newToFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    public void setNfcMap(Map<Integer, NewConditional> nfcMap) {
        this.nfcMap = nfcMap;
    }

    public void writeCandidatePair(CandidatePair candidatePair) {
        //todo: this
        if (candidatePairFolder != null)
            try {
                PrintWriter writer = new PrintWriter(candidatePairFolder + "/" + candidatePair.getNumber() + ".txt", "UTF-8");
                writer.print(candidatePair.toFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

