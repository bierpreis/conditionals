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
        File fileToRead = new File(folderToSave + "/" + numberOfConditionals + "/");
        System.out.println("files to read: ");

        //todo: candidate pairs should be in order. check this
        CandidatePair candidatePair = new CandidatePair("test");
        for (File file : fileToRead.listFiles()) {
            if (!file.isDirectory()) {
                System.out.println(file.getName());

            }


        }
        return new CandidatePair("test");

    }
}
