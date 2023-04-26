package com.alibaba.csp.sentinel.dashboard.rule.nacos.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.RuleNacosPublisher;
import org.springframework.stereotype.Component;

@Component("degradeRuleNacosPublisher")
public class DegradeRuleNacosPublisher extends RuleNacosPublisher<DegradeRuleEntity> {
    @Override
    public String getDataIdPostfix() {
        return NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX;
    }
}
