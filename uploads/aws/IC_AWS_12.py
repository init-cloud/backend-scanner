from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_AWS_12(BaseResourceCheck):
    role_list = list()

    def __init__(self) -> None:
        name = "Do not declare the same role and policy repeatedly"
        id = "IC_AWS_12"
        supported_resources = ("aws_iam_role_policy_attachment",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'role' in conf.keys() and 'policy_arn' in conf.keys():
            role_name = conf['role'][0]
            policy_arn = conf['policy_arn'][0]
            if IC_AWS_12.role_list is not None:
                for role in IC_AWS_12.role_list:
                    if role_name in role['role_name'] and policy_arn in role['policy_arn']:
                        return CheckResult.FAILED
            role_dict = {'role_name': role_name, 'policy_arn': policy_arn}
            IC_AWS_12.role_list.append(role_dict)
            return CheckResult.PASSED


check = IC_AWS_12()
