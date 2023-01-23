from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_NCP_4(BaseResourceCheck):
    subnet_no = list()

    def __init__(self) -> None:
        name = "Avoid allocating the same routing table over and over again."
        id = "IC_NCP_4"
        supported_resources = ("ncloud_route_table_association",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if "subnet_no" in conf.keys():
            if IC_NCP_4.subnet_no is not None:
                if conf["subnet_no"][0] in IC_NCP_4.subnet_no:
                    return CheckResult.FAILED
            IC_NCP_4.subnet_no.append(conf["subnet_no"][0])
            return CheckResult.PASSED


check = IC_NCP_4()
