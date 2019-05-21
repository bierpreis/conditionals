package kb_creator.model.Conditionals;

import java.io.IOException;
import java.io.PrintWriter;

public class KBWriter {

    public KBWriter() {

    }

    public void writeToFile(KnowledgeBase knowledgeBase) {
        try {
            PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
            writer.print(knowledgeBase.toFileString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
