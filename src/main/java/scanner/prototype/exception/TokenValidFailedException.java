package scanner.prototype.exception;

public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super("Failed.");
    }
    
    private TokenValidFailedException(String message) {
        super(message);
    }
    
    public TokenValidFailedException(String message, Throwable cause) { 
        super(message, cause); 
    }

    public TokenValidFailedException(Exception ex) {
    }
}
