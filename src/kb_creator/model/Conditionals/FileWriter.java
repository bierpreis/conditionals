package kb_creator.model.Conditionals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

//todo: make faster: 1 kb writer for n element kbs. then open the 2 folders for those kbs ant write and not open for every file.
public class FileWriter {
    File filePathToSave;
    private Map<Integer, NewConditional> nfcMap;

    public FileWriter(File filePathToSave) {
        this.filePathToSave = filePathToSave;


    }

    public void writeConsistentKBToFile(KnowledgeBase knowledgeBase) {
        File dir;
        if (filePathToSave != null) {
            dir = new File(filePathToSave.getAbsolutePath() + "/KBs/" + knowledgeBase.getSize() + "/Consistent/");

            dir.mkdirs();

            try {
                PrintWriter writer = new PrintWriter(dir.toString() + "/" + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.newToFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeInconsistentKBToFile(KnowledgeBase knowledgeBase) {
        File dir;
        if (filePathToSave != null) {
            dir = new File(filePathToSave.getAbsolutePath() + "/KBs/" + knowledgeBase.getSize() + "/Inconsistent/");

            dir.mkdirs();

            try {
                PrintWriter writer = new PrintWriter(dir.toString() + "/" + knowledgeBase.getKbNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.newToFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNfcMap(Map<Integer, NewConditional> nfcMap) {
        this.nfcMap = nfcMap;
    }
}

