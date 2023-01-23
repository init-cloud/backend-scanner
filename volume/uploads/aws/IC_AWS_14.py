from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_AWS_14(BaseResourceCheck):
    check_resource_duplicated = False

    def __init__(self) -> None:
        name = "Declare the aws_ebs_default_kms_key resource only once in the same account/region"
        id = "IC_AWS_14"
        supported_resources = ("aws_ebs_default_kms_key",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'key_arn' in conf.keys() and IC_AWS_14.check_resource_duplicated is False:
            IC_AWS_14.check_resource_duplicated = True
            return CheckResult.PASSED
        else:
            return CheckResult.FAILED


check = IC_AWS_14()
