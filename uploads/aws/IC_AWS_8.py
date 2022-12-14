from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


def check_resource_conf(conf, arg, arg_list):
    if arg in conf.keys() and 'policy_arn' in conf.keys():
        policy_arn = conf['policy_arn'][0]
        args = conf[arg][0]
        if arg_list is not None:
            for arg_inst in arg_list:
                for args_ in args:
                    if args_ in arg_inst[arg] and policy_arn in arg_inst['policy_arn']:
                        return CheckResult.FAILED
        arg_dict = {arg: args, 'policy_arn': policy_arn}
        arg_list.append(arg_dict)
        return CheckResult.PASSED
    return CheckResult.PASSED


class IC_AWS_8(BaseResourceCheck):
    user_list, role_list, group_list = list(), list(), list()

    def __init__(self) -> None:
        name = "Do not declare the same user/policy/groups twice"
        id = "IC_AWS_8"
        supported_resources = ("aws_iam_policy_attachment",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        user_result = check_resource_conf(conf, 'users', IC_AWS_8.user_list)
        role_result = check_resource_conf(conf, 'roles', IC_AWS_8.role_list)
        group_result = check_resource_conf(conf, 'groups', IC_AWS_8.group_list)
        if user_result == CheckResult.FAILED or role_result == CheckResult.FAILED or group_result == CheckResult.FAILED:
            return CheckResult.FAILED
        else:
            return CheckResult.PASSED


check = IC_AWS_8()
