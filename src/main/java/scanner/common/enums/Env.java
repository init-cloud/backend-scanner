package scanner.common.enums;

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

    private static final String AWS = "aws";
    private static final String NCP = "ncp";
    private static final String NCLOUD = "ncloud";
    private static final String OPENSTACK = "openstack";

    public static String getCSPExternalPath(String provider){

        if(provider.hashCode() == AWS.hashCode())
            return AWS_EXTERNAL_CHECK.getValue();

        else if (provider.hashCode() == NCLOUD.hashCode() || provider.hashCode() == NCP.hashCode())
            return NCP_EXTERNAL_CHECK.getValue();

        else if(provider.hashCode() == OPENSTACK.hashCode())
            return OPEN_EXTERNAL_CHECK.getValue();

        else
            return NONE_EXTERNAL_CHECK.getValue();
    }

    public static String getCSP(String provider){

        if(provider.hashCode() == AWS.hashCode())
            return AWS;

        else if (provider.hashCode() == NCLOUD.hashCode() || provider.hashCode() == NCP.hashCode())
            return NCLOUD;

        else if(provider.hashCode() == OPENSTACK.hashCode())
            return OPENSTACK;

        else
            return "none";
    }
}
