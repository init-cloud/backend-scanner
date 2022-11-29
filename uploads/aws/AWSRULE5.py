from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class AWSRULE5(BaseResourceCheck):
    role_list = list()
    roles_list = list()

    def __init__(self) -> None:
        name = "Do not declare the same rule for aws_iam_user_policy_attachment and aws_iam_policy_attachment ."
        id = "IC_AWS_5"
        supported_resources = ("aws_iam_policy_attachment", "aws_iam_role_policy_attachment")
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'roles' in conf.keys():
            roles = conf['roles'][0]
            AWSRULE5.roles_list.append(roles)
            for role in roles:
                if role in AWSRULE5.role_list:
                    return CheckResult.FAILED
            return CheckResult.PASSED
        if 'role' in conf.keys():
            role = conf['role'][0]
            AWSRULE5.role_list.append(role)
            for roles in AWSRULE5.roles_list:
                if role in roles:
                    return CheckResult.FAILED
            return CheckResult.PASSED


check = AWSRULE5()
