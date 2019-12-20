package com.supergo.manager.feign;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FeignCOnfig
 * @Description TODO
 * @Author wesker
 * @Date 7/25/2019 12:10 PM
 * @Version 1.0
 **/
@Configuration
public class FeignConfig {

    public static int connectTimeOutMillis = 50000;
    public static int readTimeOutMillis = 50000;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}
