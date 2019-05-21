package kb_creator.model.Conditionals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class KBWriter {
    File filePathToSave;

    public KBWriter(File filePathToSave) {
        this.filePathToSave = filePathToSave;

    }

    public void writeToFile(KnowledgeBase knowledgeBase, int number) {
        System.out.println("filepath: " + filePathToSave);
        File dir = new File("folder");
        dir.mkdir();
        try {
            PrintWriter writer = new PrintWriter("./" + "folder/" + number + ".txt", "UTF-8");
            writer.print(knowledgeBase.toFileString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
