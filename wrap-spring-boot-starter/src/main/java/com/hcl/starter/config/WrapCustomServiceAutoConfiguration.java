package com.hcl.starter.config;

import com.hcl.starter.service.WrapCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Hucl
 * @date: 2019/9/19 14:03
 * @description:
 */

@Configuration
@ConditionalOnClass(WrapCustomService.class)
// 指定配置属性封装类
@EnableConfigurationProperties(WrapCustomServiceProperties.class)
public class WrapCustomServiceAutoConfiguration {

    /**
     * 在此，虽然WrapCustomServiceProperties本身并没有被spring容器管理，
     * 但是在 ‘@EnableConfigurationProperties(WrapCustomServiceProperties.class)’ 这一步，
     * 会把WrapCustomServiceProperties注册到spring容器中，所以可以直接拿来用。
     */
    @Autowired
    private WrapCustomServiceProperties properties;

    @Bean
    @ConditionalOnProperty(name = "wrap.service.enabled", havingValue = "true", matchIfMissing = true)
//    @ConditionalOnMissingBean
    public WrapCustomService wrapCustomService() {
        return new WrapCustomService(properties.getPrefix(), properties.getSuffix());
    }

    @Bean
    @ConditionalOnMissingBean
    public WrapCustomService wrapCustomServiceMissBean() {
        return new WrapCustomService("", "");
    }
}
