package scanner.security.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class SecurityUtil {

    private SecurityUtil(){
        throw new IllegalStateException("Utility class");
    }

    public static String getUsername(){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return user.getUsername();
    }
}
