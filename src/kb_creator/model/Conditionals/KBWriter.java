package kb_creator.model.Conditionals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class KBWriter {
    File filePathToSave;

    public KBWriter(File filePathToSave) {
        this.filePathToSave = filePathToSave;

    }

    public void writeToFile(KnowledgeBase knowledgeBase) {
        File dir;
        if (filePathToSave != null) {
            dir = new File(filePathToSave.getAbsolutePath() + "/KBs/" + knowledgeBase.getSize() + "/");

            dir.mkdirs();

            try {
                PrintWriter writer = new PrintWriter(dir.toString() + "/" + knowledgeBase.getNumber() + ".txt", "UTF-8");
                writer.print(knowledgeBase.newToFileString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
