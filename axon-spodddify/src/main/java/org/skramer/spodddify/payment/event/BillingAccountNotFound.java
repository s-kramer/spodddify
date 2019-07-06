package org.skramer.spodddify.payment.event;

import lombok.Value;

@Value
public class BillingAccountNotFound {
    private String accountId;
}
