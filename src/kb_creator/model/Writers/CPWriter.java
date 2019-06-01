package kb_creator.model.Writers;

import gherkin.lexer.Fi;
import kb_creator.model.Conditionals.CandidatePair;

import java.io.File;

//todo: thread for this
public class CPWriter {
    private String pathToSave;

    public CPWriter(String pathToSave) {
        if (pathToSave != null) {
            this.pathToSave = pathToSave + "/tmp/";

            File tmpFile = new File(pathToSave);
            tmpFile.mkdirs();
        }
    }

    public void writePair(CandidatePair candidatePair) {
        //todo
    }

    public void deletePairs(int numberOfConditionals) {
        //todo
    }
}
