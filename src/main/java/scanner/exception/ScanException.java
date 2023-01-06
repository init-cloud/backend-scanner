package scanner.exception;

public class ScanException extends RuntimeException{
    public ScanException(String message) { 
        super(message); 
    } 
    
    public ScanException(String message, Throwable cause) { 
        super(message, cause); 
    }

    public ScanException(Exception ex) {
    }
}
