from checkov.common.models.enums import CheckCategories, CheckResult
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck
from DB import Parameters


class LBListenerUsesSecureProtocols(BaseResourceCheck):

    params = Parameters()
    param_list = params.get_param_env("CKV_NCP_13")

    def __init__(self):
        name = "Ensure LB Listener uses only secure protocols"
        id = "CKV_NCP_13"
        supported_resources = ('ncloud_lb_listener',)
        categories = (CheckCategories.NETWORKING,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if 'protocol' in conf.keys():
            protocol = conf['protocol'][0]
            if protocol in ('HTTPS', 'TLS'):
                if 'tls_min_version_type' in conf.keys():
                    if self.is_valid_tls_version(conf['tls_min_version_type'], [self.param_list[0]['value']]):
                        return CheckResult.PASSED
            return CheckResult.FAILED

    def is_valid_tls_version(self, conf: list, tls: list) -> bool:
        tls_versions = (["TLSV10"], ["TLSV11"], ["TLSV12"], ["TLSV13"])

        if tls not in tls_versions:
            return False

        if conf['tls_min_version_type'] == [self.param_list[0]['value']]:
                return CheckResult.PASSED

        if tls_versions.index(tls) > tls_versions.index(conf['tls_min_version_type']):
            return True
        
        return False


check = LBListenerUsesSecureProtocols()
