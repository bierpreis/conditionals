package kb_creator.model.Writers;

import kb_creator.model.Conditionals.CandidatePair;
import kb_creator.model.Conditionals.KnowledgeBase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class KBWriter {
    //todo: own thread for this

    private String consistentKbFolder;
    private String inconsistentKbFolder;
    private String candidatePairFolder;

    public KBWriter(String filePathToSave, int kbAmount) {

        //if filepath is null nothing should be saved so nothing should happen here
        if (filePathToSave != null) {
            filePathToSave = filePathToSave + "/" + kbAmount;


            consistentKbFolder = filePathToSave + "/Consistent/";
            inconsistentKbFolder = filePathToSave + "/Inconsistent/";
            candidatePairFolder = filePathToSave + "/CandidatePair/";

            //create folders

            File consistentFolder = new File(consistentKbFolder);
            consistentFolder.mkdirs();

            File inconsistentFolder = new File(inconsistentKbFolder);
            inconsistentFolder.mkdirs();

            File candidatepairFolder = new File(candidatePairFolder);
            candidatepairFolder.mkdirs();


        }
    }

    public void writeConsistentKBToFile(KnowledgeBase knowledgeBase) {

        if (consistentKbFolder != null)
            try {


                PrintWriter writer = new PrintWriter(consistentKbFolder + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.newToFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    //todo: theese get written in wrong folder (folder for k -1)
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


}

