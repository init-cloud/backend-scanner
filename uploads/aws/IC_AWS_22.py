from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_AWS_22(BaseResourceCheck):
    bucket_name = list()

    def __init__(self) -> None:
        name = "Do not declare the same bucket name repeatedly."
        id = "IC_AWS_22"
        supported_resources = ("aws_s3_bucket_inventory",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'name' in conf.keys():
            bucket = conf['name'][0]
            if bucket in self.bucket_name:
                return CheckResult.FAILED

            self.bucket_name.append(bucket)
            return CheckResult.PASSED


check = IC_AWS_22()
