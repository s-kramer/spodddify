package org.skramer.spodddify.payment.event;


import lombok.Value;

@Value
public class BillingAccountCharged {
    private String accountId;
    private long chargeAmount;
}
