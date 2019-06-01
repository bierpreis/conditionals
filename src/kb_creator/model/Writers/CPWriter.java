package kb_creator.model.Writers;

import kb_creator.model.Conditionals.CandidatePair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

//todo: thread for this
public class CPWriter {
    private String folderToSave;

    public CPWriter(String folderToSave) {
        if (folderToSave != null) {
            this.folderToSave = folderToSave + "/tmp/";

            File tmpFile = new File(folderToSave);
            tmpFile.mkdirs();
        }
    }

    //todo
    public void writePair(CandidatePair candidatePair) {


        //todo: this
        if (folderToSave != null)
            try {
                PrintWriter writer = new PrintWriter(folderToSave + "/" + candidatePair.getNumber() + ".txt", "UTF-8");
                writer.print(candidatePair.toFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void deletePairs(int numberOfConditionals) {
        //todo
    }

    //todo
    public CandidatePair readNextPair(int numberOfConditionals) {
        //read String
        //create Pair from String
        CandidatePair candidatePair = new CandidatePair("test");

        return candidatePair;
    }
}
