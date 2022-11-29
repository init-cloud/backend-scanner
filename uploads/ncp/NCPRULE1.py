from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class NCPRULE1(BaseResourceCheck):
    acg_group_no = list()

    def __init__(self) -> None:
        name = "Do not assign multiple rules to the same acg."
        id = "IC_NCP_1"
        supported_resources = ("ncloud_access_control_group_rule",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'access_control_group_no' in conf.keys():
            if NCPRULE1.acg_group_no is not None:
                if conf['access_control_group_no'][0] in NCPRULE1.acg_group_no:
                    return CheckResult.FAILED

            NCPRULE1.acg_group_no.append(conf['access_control_group_no'][0])
            return CheckResult.PASSED


check = NCPRULE1()
