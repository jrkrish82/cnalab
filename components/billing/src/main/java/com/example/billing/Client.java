package com.example.billing;


import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


public class Client {

    private final RestOperations restTemplate;



    private String billingServiceURL;

    public Client(String billingServiceURL) {
        this.restTemplate = new RestTemplate();
        this.billingServiceURL = billingServiceURL;
    }

    public void billUser(String userId, int amount) {

        restTemplate.postForEntity(billingServiceURL + "/reocurringPayment", amount, String.class);




    }
}
