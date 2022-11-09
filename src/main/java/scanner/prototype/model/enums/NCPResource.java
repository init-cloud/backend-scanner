package scanner.prototype.model.enums;

import lombok.Getter;

@Getter
public enum NCPResource {
    LB_TARGET_GROUP("ncloud_lb_target_group"), 
    ACCESS_CONTROL_GROUP_ID("ncloud_access_control_group_rule"), 
    SERVER("ncloud_server"), 
    LAUNCH_CONFIGURATION("ncloud_launch_configuration"), 
    NETWORK_ACL_RULE("ncloud_network_acl_rule");

    private final String resource;

    private NCPResource (String resource) { 
        this.resource = resource; 
    }
}
