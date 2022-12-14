from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class IC_AWS_17(BaseResourceCheck):
    bucket_name = list()

    def __init__(self) -> None:
        name = "Do not declare the same bucket name repeatedly."
        id = "IC_AWS_17"
        supported_resources = ("aws_s3_bucket_accelerate_configuration",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'bucket' in conf.keys():
            bucket = conf['bucket'][0]
            if bucket in self.bucket_name:
                return CheckResult.FAILED

            self.bucket_name.append(bucket)
            return CheckResult.PASSED


check = IC_AWS_17()
