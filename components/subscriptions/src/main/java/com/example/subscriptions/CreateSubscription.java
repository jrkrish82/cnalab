package com.example.subscriptions;

import com.example.billing.Client;
import com.example.email.SendEmail;

public class CreateSubscription {

    private final Client client;
    private final SendEmail emailSender;
    private final SubscriptionRepository subscriptions;

    public CreateSubscription(
            Client client,
            SendEmail emailSender, SubscriptionRepository subscriptions) {
        this.client = client;
        this.emailSender = emailSender;
        this.subscriptions = subscriptions;
    }

    public void run(String userId, String packageId) {
        subscriptions.create(new Subscription(userId, packageId));
        //chargeUser.run(userId, 100);
        client.billUser("abc123",100);
        emailSender.run("me@example.com", "Subscription Created", "Some email body");
    }
}
