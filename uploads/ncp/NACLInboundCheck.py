from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck
from checkov.common.models.enums import CheckResult, CheckCategories


class NACLInboundCheck(BaseResourceCheck):
    def __init__(self, check_id, ip_block, port):
        name = f"Ensure no NACL allow inbound from {ip_block} to port {port}"
        id = check_id
        supported_resources = ('ncloud_network_acl_rule',)
        categories = (CheckCategories.NETWORKING,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)
        self.ip_block = ip_block
        self.port = int(port)

    def scan_resource_conf(self, conf):
        for inbound in conf.get('inbound', []):
            if inbound['rule_action'] == ["ALLOW"]:
                ip = inbound.get('ip_block', [self.ip_block])
                if ip == [self.ip_block]:
                    port = inbound.get('port_range', str(self.port))[0]
                    if port == str(self.port):
                        return CheckResult.FAILED
                    elif port.find('-'):
                        port_range = list(map(int, port.split("-")))
                        if port_range[0] <= self.port <= port_range[-1]:
                            return CheckResult.FAILED

        return CheckResult.PASSED