from AccessControlGroupInboundRule import AccessControlGroupInboundRule
from param import Parameters


class AccessControlGroupRuleInboundPort22(AccessControlGroupInboundRule):
    def __init__(self):
        params = Parameters()
        param_list = params.get_param_env("CKV_NCP_4")
        super().__init__(check_id="CKV_NCP_4_CUSTOM", ip_block=param_list[0]["value"], port=param_list[1]["value"])


check = AccessControlGroupRuleInboundPort22()
