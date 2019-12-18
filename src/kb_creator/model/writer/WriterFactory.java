package kb_creator.model.writer;

import kb_creator.model.writer.dummy.KbDummyWriter;
import kb_creator.model.writer.real.KbFileWriter;

public class WriterFactory {

    public static AbstractKbWriter getKbWriter(KbWriterOptions writerOptions){
    if(writerOptions.isRealWriter())
        return new KbFileWriter(writerOptions);
    else return new KbDummyWriter();

    }
}
