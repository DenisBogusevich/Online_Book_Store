package onlinebookstore.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Exception exeption) {
        super(message, exeption);
    }
}
