package org.skramer.spodddify.payment.event;

import lombok.Value;

@Value
public class BillingAccountNotFoundEvent {
    private String accountId;
}
