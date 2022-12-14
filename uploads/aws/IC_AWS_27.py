from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_AWS_27(BaseResourceCheck):
    route_table = list()
    gateway = list()

    def __init__(self) -> None:
        name = "Do not declare the same route_table_id and gateway_idrepeatedly."
        id = "IC_AWS_27"
        supported_resources = ("aws_route_table_association",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if ("route_table_id" and "gateway_id") in conf.keys():
            route_table_id = conf["route_table_id"][0]
            gateway_id = conf["gateway_id"][0]
            if route_table_id in self.route_table:
                return CheckResult.FAILED
            self.route_table.append(route_table_id)
            if gateway_id in self.gateway:
                return CheckResult.FAILED
            self.gateway.append(gateway_id)
            return CheckResult.PASSED


check = IC_AWS_27()
