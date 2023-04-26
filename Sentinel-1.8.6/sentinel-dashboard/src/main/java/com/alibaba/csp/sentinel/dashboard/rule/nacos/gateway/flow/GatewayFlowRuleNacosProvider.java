package com.alibaba.csp.sentinel.dashboard.rule.nacos.gateway.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.RuleNacosProvider;
import org.springframework.stereotype.Component;

@Component("gatewayFlowRuleNacosProvider")
public class GatewayFlowRuleNacosProvider extends RuleNacosProvider<GatewayFlowRuleEntity> {

    @Override
    public String getDataIdPostfix() {
        return NacosConfigUtil.GATEWAY_FLOW_DATA_ID_POSTFIX;
    }
}
