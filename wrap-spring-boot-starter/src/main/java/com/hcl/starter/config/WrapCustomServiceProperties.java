package com.hcl.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: Hucl
 * @date: 2019/9/19 14:04
 * @description:
 */

@Data
@ConfigurationProperties("wrap.service")
public class WrapCustomServiceProperties {

    //wrap.service.prefix
    //wrap.service.suffix
    private String prefix;

    private String suffix;
}
