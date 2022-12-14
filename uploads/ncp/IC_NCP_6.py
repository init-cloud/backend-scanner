from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_NCP_6(BaseResourceCheck):
    network_acl_no = list()
    inbound_priority = list()
    inbound_ip_block = list()
    inbound_port_range = list()

    def __init__(self) -> None:
        name = "1) If the priority is the same, only one of the two apply is applied each time you apply." \
               "2) If the priorities are different, both are applied, but all rules disappear when applied."
        id = "IC_NCP_6"
        supported_resources = ("ncloud_network_acl_rule",)
        categories = (CheckCategories.NETWORKING,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if "network_acl_no" in conf.keys():
            acl_no = conf["network_acl_no"][0]
            if acl_no in IC_NCP_6.network_acl_no:
                return CheckResult.FAILED
            IC_NCP_6.network_acl_no.append(acl_no)
            if "inbound" in conf.keys():
                inbound_conf = conf["inbound"][0]
                if "priority" in inbound_conf.keys():
                    priority = inbound_conf["priority"][0]
                    if priority in IC_NCP_6.inbound_priority:
                        return CheckResult.FAILED
                    IC_NCP_6.inbound_priority.append(priority)
                    if ("ip_block" and "port_range") in inbound_conf.keys():
                        ip_block = inbound_conf["ip_block"][0]
                        port_range = inbound_conf["port_range"][0]
                        if ip_block in IC_NCP_6.inbound_ip_block and port_range in IC_NCP_6.inbound_port_range:
                            return CheckResult.FAILED
                        IC_NCP_6.inbound_ip_block.append(ip_block)
                        IC_NCP_6.inbound_port_range.append(port_range)
            return CheckResult.PASSED


check = IC_NCP_6()
