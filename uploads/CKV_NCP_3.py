from __future__ import annotations
from typing import Any
from param import Parameters
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck
from checkov.common.models.enums import CheckResult, CheckCategories


class AccessControlGroupOutboundRule(BaseResourceCheck):

    def __init__(self, ip_block=None) -> None:
        params = Parameters()
        param_list = params.get_param_env("CKV_NCP_3")
        self.ip_block = param_list[0]["value"]
        name = f"Ensure no security group rules allow outbound traffic to {self.ip_block}."
        id = "CKV_NCP_3"
        supported_resources = ("ncloud_access_control_group_rule",)
        categories = (CheckCategories.NETWORKING,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)
        

    def scan_resource_conf(self, conf: dict[str, list[Any]]) -> CheckResult:
        for outbound in conf.get("outbound", []):
            ip = outbound.get("ip_block")
            if ip == ["0.0.0.0/0"] or ip == ["::/0"]:
                return CheckResult.FAILED
        return CheckResult.PASSED


check = AccessControlGroupOutboundRule()
