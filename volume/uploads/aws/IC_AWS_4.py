from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_AWS_4(BaseResourceCheck):
    user_list = list()
    users_list = list()

    def __init__(self) -> None:
        name = "Do not declare the same user for aws_iam_user_policy_attachment, aws_iam_policy_attachment."
        id = "IC_AWS_4"
        supported_resources = ("aws_iam_policy_attachment", "aws_iam_user_policy_attachment")
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'users' in conf.keys():
            users = conf['users'][0]
            IC_AWS_4.users_list.append(users)
            for user in users:
                if user in IC_AWS_4.user_list:
                    return CheckResult.FAILED
            return CheckResult.PASSED
        if 'user' in conf.keys():
            user = conf['user'][0]
            IC_AWS_4.user_list.append(user)
            for users in IC_AWS_4.users_list:
                if user in users:
                    return CheckResult.FAILED
            return CheckResult.PASSED


check = IC_AWS_4()
