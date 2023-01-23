from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_NCP_5(BaseResourceCheck):
    auto_scaling_policy_name = list()

    def __init__(self) -> None:
        name = "If the same name is declared repeatedly, one is not declared."
        id = "IC_NCP_5"
        supported_resources = ("ncloud_auto_scaling_policy",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if "name" in conf.keys():
            name = conf["name"][0]
            if len(IC_NCP_5.auto_scaling_policy_name) > 0:
                if name in IC_NCP_5.auto_scaling_policy_name:
                    return CheckResult.FAILED

            IC_NCP_5.auto_scaling_policy_name.append(name)
            return CheckResult.PASSED


check = IC_NCP_5()
