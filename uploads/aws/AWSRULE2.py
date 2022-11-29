from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class AWSRULE2(BaseResourceCheck):
    group_list = list()

    def __init__(self) -> None:
        name = "Do not declare the same group and policy repeatedly."
        id = "IC_AWS_2"
        supported_resources = ("aws_iam_group_policy_attachment",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'group' in conf.keys() and 'policy_arn' in conf.keys():
            group_name = conf['group'][0]
            policy_arn = conf['policy_arn'][0]
            if AWSRULE2.group_list is not None:
                for group in AWSRULE2.group_list:
                    if group_name in group['group_name'] and policy_arn in group['policy_arn']:
                        return CheckResult.FAILED
            group_dict = {'group_name': group_name, 'policy_arn': policy_arn}
            AWSRULE2.group_list.append(group_dict)
            return CheckResult.PASSED


check = AWSRULE2()
