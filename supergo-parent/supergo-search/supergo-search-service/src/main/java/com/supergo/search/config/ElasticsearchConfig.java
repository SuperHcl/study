package com.supergo.search.config;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ElasticsearchConfig
 * @Description TODO
 * @Author wesker
 * @Date 7/12/2019 5:42 PM
 * @Version 1.0
 **/
@Configuration
public class ElasticsearchConfig {

    private String esCluster = "192.168.66.66:9300";

    private List<TransportAddress> list = new ArrayList<>();

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
        return new ElasticsearchTemplate(client_single());
    }

   @Bean
    public Client client_single() {
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY);
            client.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.66.66"), 9300));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    /*@Bean
    public Client client_cluster() {
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY);
            client.addTransportAddresses(this.getTransportAddresses());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }*/

    private TransportAddress[] getTransportAddresses() throws UnknownHostException {
        if (StringUtils.isNotBlank(esCluster)) {
            String[] urlList = esCluster.split(",");
            for (int i = 0; i < urlList.length; i++) {
                String[] ipAndPort = urlList[i].split(":");
                TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(ipAndPort[0]), Integer.valueOf(ipAndPort[1]));
                list.add(transportAddress);
            }
        }
        return list.toArray(new TransportAddress[list.size()]);
    }
}
