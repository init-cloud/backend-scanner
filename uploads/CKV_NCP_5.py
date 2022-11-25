from AccessControlGroupInboundRule import AccessControlGroupInboundRule
from param import Parameters


class AccessControlGroupRuleInboundPort3389(AccessControlGroupInboundRule):
    def __init__(self):
        params = Parameters()
        param_list = params.get_param_env("CKV_NCP_5")
        super().__init__(check_id="CKV_NCP_5", ip_block = param_list[0]["value"],port=int(param_list[1]["value"]))


check = AccessControlGroupRuleInboundPort3389()
