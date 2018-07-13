package com.example.billing;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;



public class Client {

    private final RestOperations restTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EurekaClient discoveryClient;




    public Client() {
        this.restTemplate = new RestTemplate();

    }

    @HystrixCommand(fallbackMethod = "defaultBillingService")
    public void billUser(String userId, int amount) {

        restTemplate.postForEntity(fetchBillingServiceUrl() + "/reocurringPayment", amount, String.class);

    }

    private String fetchBillingServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("billing", false);
        System.out.println("instanceID:" + instance.getId());

      String billingServiceURL = instance.getHomePageUrl();
        System.out.println("billing service Url: " + billingServiceURL);

        return billingServiceURL;
    }

    public void defaultBillingService(String userId, int amount) {
        logger.info("DBilling service fallback, url down userid: {} amount:{}",userId,amount);
        //return "This BillingService is down now";
    }


}
