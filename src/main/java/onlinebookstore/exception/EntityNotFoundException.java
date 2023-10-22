package onlinebookstore.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message, Exception exeption) {
        super(message, exeption);
    }
}
