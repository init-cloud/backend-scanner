package scanner.response.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /* Status */
    STATUS_1001(1001, "Request Running."),

    /* Success */
    STATUS_2001(2000, "Request done."),
    STATUS_2002(2001, "Access."),
    STATUS_2003(2002, "Authenticated."),

    /* Redirection. */
    STATUS_3001(3001, "Redirected."),

    /* Invalid Request */
    STATUS_4001(4001, "Expired."),
    STATUS_4002(4002, "Invalid Image format."),
    STATUS_4003(4003, "Invalid Token."),
    STATUS_4004(4004, "Request invalid data."),
    STATUS_4005(4005, "Data missing."),
    STATUS_4007(4007, "Invalid Data recevied."),

    /* Server Error. */
    STATUS_5001(5001, "Server busy."),
    STATUS_5100(5100, "Unknown error.");

    private final int code;
    private final String message;
}