package com.flux.fluxdemo.router;

import com.flux.fluxdemo.handler.CityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author Hu.ChangLiang
 * @date 2022/9/13 17:17
 */
@Configuration
public class CityRouter {
    @Bean
    public RouterFunction<ServerResponse> routerCity(CityHandler cityHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), cityHandler::helloCity);
    }
}
