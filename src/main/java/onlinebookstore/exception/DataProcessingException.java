package onlinebookstore.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Exception error) {
        super(massage, error);
    }
}
