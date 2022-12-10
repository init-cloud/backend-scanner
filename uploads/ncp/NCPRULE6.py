from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class NCPRULE5(BaseResourceCheck):
    subnet_no = list()

    def __init__(self) -> None:
        name = "Avoid allocating the same routing table over and over again."
        id = "IC_NCP_6"
        supported_resources = ("ncloud_route_table_association",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if "subnet_no" in conf.keys():
            if NCPRULE5.subnet_no is not None:
                if conf["subnet_no"][0] in NCPRULE5.subnet_no:
                    return CheckResult.FAILED
            NCPRULE5.subnet_no.append(conf["subnet_no"][0])
            return CheckResult.PASSED


check = NCPRULE5()
