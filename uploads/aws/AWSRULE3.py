from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class AWSRULE3(BaseResourceCheck):
    name_list = list()

    def __init__(self) -> None:
        name = "Do not declare with same name in aws_iam_role_policy"
        id = "IC_AWS_3"
        supported_resources = ("aws_iam_role_policy",)
        categories = (CheckCategories.KUBERNETES,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'name' in conf.keys():
            name = conf['name'][0]
            if isinstance(name, list):
                name = name[0]
            if name in AWSRULE3.name_list:
                return CheckResult.FAILED
            else:
                AWSRULE3.name_list.append(name)
                return CheckResult.PASSED
        return CheckResult.UNKNOWN


check = AWSRULE3()
