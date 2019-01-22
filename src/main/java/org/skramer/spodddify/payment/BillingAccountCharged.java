package org.skramer.spodddify.payment;

import lombok.Value;

@Value
class BillingAccountCharged {
    private String billingAccountId;
    private long chargeAmount;
}
