package com.nisq.mybatis.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-03-199:38
 */
@Component
public class AppConfig {


    @Value("${system.configName}")
    private String configName;


    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
}
