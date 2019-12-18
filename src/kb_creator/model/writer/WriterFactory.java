package kb_creator.model.writer;

import kb_creator.model.writer.dummy.KbDummyWriter;
import kb_creator.model.writer.real.KbFileWriter;

public class WriterFactory {

    // the reason for this class is that the writer type is in writer options
    // so cant instantiate writer of unknown type
    public static AbstractKbWriter getKbWriter(KbWriterOptions writerOptions){
    if(writerOptions.isRealWriter())
        return new KbFileWriter(writerOptions);
    else return new KbDummyWriter();

    }
}
