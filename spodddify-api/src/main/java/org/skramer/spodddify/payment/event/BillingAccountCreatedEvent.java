package org.skramer.spodddify.payment.event;

import lombok.Value;

@Value
public class BillingAccountCreatedEvent {
    private String accountId;
    private long balance;
}
