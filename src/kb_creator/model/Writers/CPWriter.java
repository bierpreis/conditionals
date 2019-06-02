package kb_creator.model.Writers;

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

    //todo: this somehow doenst work
    public void deleteFiles(int numberOfConditionals) {
        System.out.println("trying to delete " + numberOfConditionals + " element pairs");
        File fileToDelete = new File(folderToSave + "/" + numberOfConditionals + "/");
        try {
            for (File file : fileToDelete.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();

                }

            }
        } catch (NullPointerException e) {
            System.out.println("no " + numberOfConditionals + " element pairs found for deleting");
        }
    }

    //todo
    public CandidatePair readNextPair(int numberOfConditionals) {
        //read String

        CandidatePair candidatePair = new CandidatePair("test");

        return candidatePair;
    }
}
