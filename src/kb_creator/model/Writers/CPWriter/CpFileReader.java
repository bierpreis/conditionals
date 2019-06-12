package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersListPair;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CpFileReader {
    private String filePath;
    private int requestedK;

    public CpFileReader(int requestedK, String filePath) {
        this.filePath = filePath;
        this.requestedK = requestedK;
    }

    public List<AbstractPair> readAllPairs() {
        List<String> fileStringArray = getPairStringList();
        return null;
    }

    private List<String> getPairStringList() {

        //read String
        File fileToRead = new File(filePath + "/" + "/tmp/" + requestedK + "/");
        System.out.println("files to read: ");


        File[] files = fileToRead.listFiles();
        Arrays.sort(files);

        List<File> fileLitst = new ArrayList<>(Arrays.asList(files));

        return null;
    }
}
