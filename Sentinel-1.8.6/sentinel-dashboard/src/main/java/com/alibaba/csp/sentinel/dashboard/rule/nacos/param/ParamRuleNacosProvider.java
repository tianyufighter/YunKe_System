package com.alibaba.csp.sentinel.dashboard.rule.nacos.param;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.RuleNacosProvider;
import org.springframework.stereotype.Component;

@Component("paramRuleNacosProvider")
public class ParamRuleNacosProvider extends RuleNacosProvider<ParamFlowRuleEntity> {

    @Override
    public String getDataIdPostfix() {
        return NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX;
    }
}
