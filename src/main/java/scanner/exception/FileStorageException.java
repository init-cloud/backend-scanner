package scanner.exception;

public class FileStorageException extends RuntimeException{
    public FileStorageException(String message) { 
        super(message); 
    } 
    
    public FileStorageException(String message, Throwable cause) { 
        super(message, cause); 
    }

    public FileStorageException(Exception ex) {
    }
}
