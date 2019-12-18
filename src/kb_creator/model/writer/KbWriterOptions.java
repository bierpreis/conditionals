package kb_creator.model.writer;

public class KbWriterOptions {


    //todo: numbers

    private boolean realWriter;

    private String filePath;

    private int requestedFileNameLength;

    private int requestedKbNumber;


    public void setRealWriter(boolean isRealWriter){
        this.realWriter = isRealWriter;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setFileNameLength(int requestedFileNameLength){
        this.requestedFileNameLength = requestedFileNameLength;
    }

    public void setRequestedKbNumber(int requestedKbNumber){
        this.requestedKbNumber = requestedKbNumber;
    }

    public String getFilePath(){
        return filePath;
    }

    public int getFileNameLength(){
        return requestedFileNameLength;
    }

    public int getRequestedKbNumber(){
        return requestedKbNumber;
    }

    public boolean isRealWriter(){
        return realWriter;
    }
}
