package org.skramer.spodddify.payment;

import lombok.Value;

@Value
class BillingAccountNotFoundEvent {
    private final String billingAccountId;
}
