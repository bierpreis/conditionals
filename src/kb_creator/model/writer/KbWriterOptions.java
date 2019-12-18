package kb_creator.model.writer;

public class KbWriterOptions {


    //todo: numbers

    private boolean realWriter;

    private String filePath;

    private int requestedFileNameLength;

    private int requestedKbNumber;



    public void setFilePath(String filePath){
        this.filePath = filePath;

        //if path is null no real writer is requested
        realWriter = filePath != null;
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
