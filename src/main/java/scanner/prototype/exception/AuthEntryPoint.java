package scanner.prototype.exception;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final static Logger log = Logger.getGlobal();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException 
    {
        authException.printStackTrace();
        log.info("Auth EnrtyPoint: " +  authException.getMessage());
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                authException.getLocalizedMessage()
        );
    }
}