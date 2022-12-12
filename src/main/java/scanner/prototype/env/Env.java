package scanner.prototype.env;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Env {
    UPLOAD_PATH("/app/uploads"),
    PARSE_API("http://initcloud_parser:8000/api/v2"),
    SHELL_COMMAND_RAW("checkov --directory ./uploads/"),

    AWS_EXTERNAL_CHECK(" --external-checks-dir ./uploads/aws"),
    NCP_EXTERNAL_CHECK(" --external-checks-dir ./uploads/ncp"),
    OPEN_EXTERNAL_CHECK(" --external-checks-dir ./uploads/openstack"),
    NONE_EXTERNAL_CHECK(" --external-checks-dir ./uploads/none");

    private final String value;

    public static String getCSPExternalPath(String provider){

        if(provider.hashCode() == "aws".hashCode())
            return AWS_EXTERNAL_CHECK.getValue();

        else if (provider.hashCode() == "ncloud".hashCode() || provider.hashCode() == "ncp".hashCode())
            return NCP_EXTERNAL_CHECK.getValue();

        else if(provider.hashCode() == "openstack".hashCode())
            return OPEN_EXTERNAL_CHECK.getValue();

        else
            return NONE_EXTERNAL_CHECK.getValue();
    }
}
