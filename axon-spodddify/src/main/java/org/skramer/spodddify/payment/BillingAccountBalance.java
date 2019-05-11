package org.skramer.spodddify.payment;

import lombok.Value;

@Value
class BillingAccountBalance {
    private String billingAccountId;
    private long balance;
}
