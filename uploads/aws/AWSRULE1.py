from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class AWSRULE1(BaseResourceCheck):
    group_list = list()
    groups_list = list()
    
    def __init__(self) -> None:
        name = "Do not declare the same group for aws_iam_group_policy_attachme and aws_iam_policy_attachment."
        id = "IC_AWS_1"
        supported_resources = ("aws_iam_policy_attachment", "aws_iam_group_policy_attachment")
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'groups' in conf.keys():
            groups = conf['groups'][0]
            AWSRULE1.groups_list.append(groups)
            for group in groups:
                if group in AWSRULE1.group_list:
                    return CheckResult.FAILED
            return CheckResult.PASSED
        if 'group' in conf.keys():
            group = conf['group'][0]
            AWSRULE1.group_list.append(group)
            for groups in AWSRULE1.groups_list:
                if group in groups:
                    return CheckResult.FAILED
            return CheckResult.PASSED


check = AWSRULE1()
