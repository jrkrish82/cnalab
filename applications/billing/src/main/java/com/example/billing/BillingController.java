package com.example.billing;

import com.example.payments.Gateway;
import com.example.payments.RecurlyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
public class BillingController {

    @Autowired
    private Gateway paymentGateway;

    public BillingController(Gateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @RequestMapping(value = "/reocurringPayment", method = RequestMethod.POST)
    public ResponseEntity<String> reoccurringPayment(@RequestBody int amount) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-type", MediaType.APPLICATION_JSON.toString());
        ResponseEntity<String> response;


            boolean result = paymentGateway.createReocurringPayment(amount);


            if (result) {
                response = new ResponseEntity<>("{errors: []}", responseHeaders, HttpStatus.CREATED);
            } else {
                response = new ResponseEntity<>("{errors: [\"error1\", \"error2\"]}", responseHeaders, HttpStatus.BAD_REQUEST);
            }

        return response;



    }

}
