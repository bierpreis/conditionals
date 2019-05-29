package kb_creator.model.Conditionals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

//todo: make faster: 1 kb writer for n element kbs. then open the 2 folders for those kbs ant write and not open for every file.
public class FileWriter {
    private Map<Integer, NewConditional> nfcMap;

    private String consistentKbFolder;
    private String inconsistentKbFolder;
    private String candidatePairFolder;

    public FileWriter(String filePathToSave, int kbAmount) {
        filePathToSave = filePathToSave + "/" + kbAmount + "/";


        consistentKbFolder = filePathToSave + "/Consistent/";
        inconsistentKbFolder = filePathToSave + "/Inconsistent/";
        candidatePairFolder = filePathToSave + "/CandidatePair/";

        //create folders
        new File(consistentKbFolder);
        new File(inconsistentKbFolder);
        new File(candidatePairFolder);
    }

    public void writeConsistentKBToFile(KnowledgeBase knowledgeBase) {


        try {
            PrintWriter writer = new PrintWriter(consistentKbFolder + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
            writer.print(knowledgeBase.newToFileString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeInconsistentKBToFile(KnowledgeBase knowledgeBase) {


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
        try {
            PrintWriter writer = new PrintWriter(inconsistentKbFolder + "/" + candidatePair.getNumber() + ".txt", "UTF-8");
            writer.print(candidatePair.toFileString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

