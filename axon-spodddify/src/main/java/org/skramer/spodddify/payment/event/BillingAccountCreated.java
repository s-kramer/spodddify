package org.skramer.spodddify.payment.event;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
public class BillingAccountCreated {
    private String listenerId;
    private long balance;
    private PaymentPlan paymentPlan;
}
