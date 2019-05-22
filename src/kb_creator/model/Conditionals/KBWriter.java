package kb_creator.model.Conditionals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class KBWriter {
    File filePathToSave;

    public KBWriter(File filePathToSave) {
        System.out.println("kbwriter: " + filePathToSave);
        this.filePathToSave = filePathToSave;

    }

    public void writeToFile(KnowledgeBase knowledgeBase) {
        //todo: fit filechooser in here
        //System.out.println("filepath: " + filePathToSave);
        File dir = new File("./folder/" + knowledgeBase.getConditionalList().size() + "/");

        dir.mkdirs();

        try {
            PrintWriter writer = new PrintWriter(dir.toString() + "/" + knowledgeBase.getNumber() + ".txt", "UTF-8");
            writer.print(knowledgeBase.toFileString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
