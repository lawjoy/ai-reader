package com.hmall.aireader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "baidu-trans")
@Data
public class BaiduTransConfig {

        private String id;
        private String secret;
        private String salt;

}
