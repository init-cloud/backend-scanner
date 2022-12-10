from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck


class AWSRULE11(BaseResourceCheck):
    aws_rule11 = list()

    def __init__(self) -> None:
        name = "Do not declare the same account repeatedly."
        id = "IC_AWS_15"
        supported_resources = ("aws_snapshot_create_volume_permission",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if ('snapshot_id' and 'account_id') in conf.keys():
            snapshot_id = conf['snapshot_id'][0]
            account_id = conf['account_id'][0]
            if AWSRULE11.aws_rule11 is not None:
                for i in AWSRULE11.aws_rule11:
                    if snapshot_id in i['snapshot_id'] and account_id in i['account_id']:
                        return CheckResult.FAILED
                    else:
                        AWSRULE11.aws_rule11.append({'snapshot_id': snapshot_id, 'account_id': account_id})
                        return CheckResult.PASSED
            AWSRULE11.aws_rule11.append({'snapshot_id': snapshot_id, 'account_id': account_id})
            return CheckResult.PASSED
        else:
            return CheckResult.UNKNOWN


check = AWSRULE11()
