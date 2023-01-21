from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_NCP_2(BaseResourceCheck):

    def __init__(self) -> None:
        name = "If max is 0, auto scaling produces nothing"
        id = "IC_NCP_2"
        supported_resources = ("ncloud_auto_scaling_group",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if "max_size" in conf.keys():
            if conf["max_size"][0] > 0:
                return CheckResult.PASSED
            else:
                return CheckResult.FAILED


check = IC_NCP_2()
