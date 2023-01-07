package scanner.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Env {
    UPLOAD_PATH("/app/uploads"),
    PARSE_API("http://initcloud_parser:8000/api/v2"),
    SHELL_COMMAND_RAW("checkov --directory /app/uploads/"),

    AWS_EXTERNAL_CHECK(" --external-checks-dir /app/external/aws"),
    NCP_EXTERNAL_CHECK(" --external-checks-dir /app/external/ncp"),
    OPEN_EXTERNAL_CHECK(" --external-checks-dir /app/external/openstack"),
    NONE_EXTERNAL_CHECK(" --external-checks-dir /app/external/none");

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

    public static String getCSP(String provider){

        if(provider.hashCode() == "aws".hashCode())
            return "aws";

        else if (provider.hashCode() == "ncloud".hashCode() || provider.hashCode() == "ncp".hashCode())
            return "ncloud";

        else if(provider.hashCode() == "openstack".hashCode())
            return "openstack";

        else
            return "none";
    }
}
