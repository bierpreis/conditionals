package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.Pairs.AbstractPair;
import kb_creator.model.Conditionals.Pairs.CandidateNumbersArrayPair;
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
        List<String> stringList = getPairStringList();
        List<AbstractPair> pairsList = new ArrayList<>(stringList.size());

        for (String stringFromFile : stringList)
            pairsList.add(new CandidateNumbersArrayPair(stringFromFile));

        return pairsList;
    }

    private List<String> getPairStringList() {

        //read String
        File fileToRead = new File(filePath + "/" + "/tmp/" + requestedK + "/");
        System.out.println("files to read: ");


        File[] filesArray = fileToRead.listFiles();

        //todo: sorting doesnt work good. implement leading 0s to file names
        Arrays.sort(filesArray);

        List<String> stringList = new ArrayList<>();

        //todo: this adds filepath and not the real string
        for (File file : filesArray) {
            stringList.add(file.toString());
        }
        System.out.println(stringList);
        return stringList;
    }
}
