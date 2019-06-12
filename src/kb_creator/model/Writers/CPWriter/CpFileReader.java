package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersListPair;

import java.io.File;
import java.util.List;

public class CpFileReader {
    private String filePath;
    private int requestedK;

    public CpFileReader(int requestedK, String filePath) {
        this.filePath = filePath;
        this.requestedK = requestedK;
    }

    public List<AbstractPair> readAllPairs() {
        List<String> fileStringList = getPairStringList();
        return null;
    }

    private List<String> getPairStringList() {

        //read String
        File fileToRead = new File(filePath + "/" + "/tmp/" + requestedK + "/");
        System.out.println("files to read: ");

        //todo: files are not in order. sort them.
        AbstractPair candidatePair = new CandidateNumbersListPair("test");
        for (File file : fileToRead.listFiles()) {
            if (!file.isDirectory()) {
                System.out.println(file.getName());

            }


        }
        return null;
    }
}
