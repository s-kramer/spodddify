package org.skramer.spodddify.payment;

import lombok.Value;

@Value
class BillingAccountBalance {
    private String listenerId;
    private long balance;
}
