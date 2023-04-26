package com.alibaba.csp.sentinel.dashboard.rule.nacos.param;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.RuleNacosPublisher;
import org.springframework.stereotype.Component;

@Component("paramRuleNacosPublisher")
public class ParamRuleNacosPublisher extends RuleNacosPublisher<ParamFlowRuleEntity> {

    @Override
    public String getDataIdPostfix() {
        return NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX;
    }
}
