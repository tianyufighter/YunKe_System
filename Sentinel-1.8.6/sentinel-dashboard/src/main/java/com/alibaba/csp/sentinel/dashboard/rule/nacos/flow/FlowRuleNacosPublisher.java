package com.alibaba.csp.sentinel.dashboard.rule.nacos.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.RuleNacosPublisher;
import org.springframework.stereotype.Component;

@Component("flowRuleNacosPublisher")
public class FlowRuleNacosPublisher extends RuleNacosPublisher<FlowRuleEntity> {

    @Override
    public String getDataIdPostfix() {
        return NacosConfigUtil.FLOW_DATA_ID_POSTFIX;
    }
}
