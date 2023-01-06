package scanner.exception;

public class CheckListException extends RuntimeException{
    public CheckListException(String message) { 
        super(message); 
    } 
    
    public CheckListException(String message, Throwable cause) { 
        super(message, cause); 
    }

    public CheckListException(Exception ex) {
    }
}
