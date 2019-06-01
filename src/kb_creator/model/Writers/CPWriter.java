package kb_creator.model.Writers;

import gherkin.lexer.Fi;
import kb_creator.model.Conditionals.CandidatePair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

//todo: thread for this
public class CPWriter {
    private String folderToSave;

    public CPWriter(String filePath) {
        if (filePath != null) {
            this.folderToSave = filePath + "/tmp/";

            File tmpFile = new File(this.folderToSave);
            tmpFile.mkdirs();
        }
    }

    public void writePair(CandidatePair candidatePair) {
        File subFolder = new File(folderToSave + "/" + candidatePair.getKnowledgeBase().getSize() + "/");
        if (!subFolder.exists())
            subFolder.mkdirs();
        if (folderToSave != null)
            try {
                PrintWriter writer = new PrintWriter(subFolder.toString() + "/" + candidatePair.getNumber() + ".txt", "UTF-8");
                writer.print(candidatePair.toFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    //todo: test
    public void deleteFiles(int numberOfConditionals) {
        File fileToDelete = new File(folderToSave + numberOfConditionals);
        boolean succsess = fileToDelete.delete();
        if (succsess)
            System.out.println("temp files for " + numberOfConditionals + " element pairs successfully deleted");
        else System.out.println("deleting " + numberOfConditionals + " element pairs failed");
    }

    //todo
    public CandidatePair readNextPair(int numberOfConditionals) {
        //read String
        //create Pair from String
        CandidatePair candidatePair = new CandidatePair("test");

        return candidatePair;
    }
}
