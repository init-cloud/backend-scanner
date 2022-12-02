package scanner.prototype.env;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Env {
    SHELL_COMMAND_RAW("checkov --directory ./uploads/"),
    EXTERNAL_CHECK(" --external-checks-dir ./uploads"),
    AWS_EXTERNAL_CHECK(" --external-checks-dir ./uploads/aws"),
    NCP_EXTERNAL_CHECK(" --external-checks-dir ./uploads/ncp");

    private final String value;

    public static String getCSPExternalPath(String provider){
        if(provider == "aws")
            return AWS_EXTERNAL_CHECK.getValue();

        else if(provider == "ncp" || provider == "ncloud")
            return NCP_EXTERNAL_CHECK.getValue();

        return EXTERNAL_CHECK.getValue();
    }
}
