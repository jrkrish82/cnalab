package com.example.ums;

import com.example.billing.Client;
import com.example.subscriptions.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@RefreshScope
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    NamedParameterJdbcTemplate datasource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {
        logger.info("********Setting up database********");
        jdbcTemplate.execute("DROP TABLE subscriptions IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE subscriptions(" +
                "id SERIAL, userId VARCHAR(255), packageId VARCHAR(255))");
    }

    @Bean
    public SubscriptionRepository subscriptionRepository() {
        return new SubscriptionRepository(datasource);
    }


    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Client billingClient() {

            return new Client();
    }
}
