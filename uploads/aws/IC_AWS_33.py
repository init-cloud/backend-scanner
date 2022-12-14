from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_AWS_33(BaseResourceCheck):
    network_acl = list()
    subnet = list()

    def __init__(self) -> None:
        name = "Do not declare the same network_acl_id and subnet_id repeatedly."
        id = "IC_AWS_33"
        supported_resources = ("aws_network_acl_association",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if ("network_acl_id" and "subnet_id") in conf.keys():
            route_table_id = conf["network_acl_id"][0]
            gateway_id = conf["subnet_id"][0]
            if route_table_id in self.network_acl:
                return CheckResult.FAILED
            self.network_acl.append(route_table_id)
            if gateway_id in self.subnet:
                return CheckResult.FAILED
            self.subnet.append(gateway_id)
            return CheckResult.PASSED


check = IC_AWS_33()
