package com.alibaba.csp.sentinel.dashboard.rule.nacos.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.RuleNacosProvider;
import org.springframework.stereotype.Component;

@Component("flowRuleNacosProvider")
public class FlowRuleNacosProvider extends RuleNacosProvider<FlowRuleEntity> {

    @Override
    public String getDataIdPostfix() {
        return NacosConfigUtil.FLOW_DATA_ID_POSTFIX;
    }
}
