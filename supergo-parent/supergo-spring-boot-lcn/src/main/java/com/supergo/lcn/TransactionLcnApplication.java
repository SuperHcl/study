package com.supergo.lcn;

// import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by on 2019/6/10.
 */
@SpringBootApplication
// @EnableTransactionManagerServer
public class TransactionLcnApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionLcnApplication.class,args);
    }
}
