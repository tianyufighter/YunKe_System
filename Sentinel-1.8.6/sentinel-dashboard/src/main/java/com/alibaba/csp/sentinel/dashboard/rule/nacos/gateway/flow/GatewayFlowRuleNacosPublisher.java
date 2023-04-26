package com.alibaba.csp.sentinel.dashboard.rule.nacos.gateway.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.RuleNacosPublisher;
import org.springframework.stereotype.Component;

@Component("gatewayFlowRuleNacosPublisher")
public class GatewayFlowRuleNacosPublisher extends RuleNacosPublisher<GatewayFlowRuleEntity> {
    @Override
    public String getDataIdPostfix() {
        return NacosConfigUtil.GATEWAY_FLOW_DATA_ID_POSTFIX;
    }
}
