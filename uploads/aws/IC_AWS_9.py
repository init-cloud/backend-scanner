from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_AWS_9(BaseResourceCheck):
    group_membership_dict = dict()

    def __init__(self) -> None:
        name = "Do not declare the same user more than once in the same groups"
        id = "IC_AWS_9"
        supported_resources = ("aws_iam_user_group_membership",)
        categories = (CheckCategories.KUBERNETES,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'groups' in conf.keys() and 'user' in conf.keys():
            groups = conf['groups'][0]
            user = conf['user'][0]
            if isinstance(groups, list):
                groups = groups[0]
            if isinstance(user, list):
                user = user[0]
            if user in IC_AWS_9.group_membership_dict.keys():
                if groups in IC_AWS_9.group_membership_dict[user]:
                    return CheckResult.FAILED
                else:
                    IC_AWS_9.group_membership_dict[user].append(groups)
                    return CheckResult.PASSED
            else:
                IC_AWS_9.group_membership_dict[user] = [groups]
                return CheckResult.PASSED
        return CheckResult.UNKNOWN


check = IC_AWS_9()
