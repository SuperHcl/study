package com.springbootelasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Hu.ChangLiang
 * @date 2023/2/27 14:15
 */
@Component
public class EsClient {
    @Value("${elasticsearch.rest.hosts}")
    private String hosts;

    @Bean
    public RestHighLevelClient initClient() {
        HttpHost[] httpHosts = Arrays.stream(hosts.split(","))
                .map(host -> {
                    String[] hostParts = host.split(":");
                    String hostName = hostParts[0];
                    int hostPort = Integer.parseInt(hostParts[1]);
                    return new HttpHost(hostName, hostPort, HttpHost.DEFAULT_SCHEME_NAME);
                }).toArray(HttpHost[]::new);
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
