package org.skramer.spodddify.payment;

import lombok.Value;

@Value
class BillingAccountCreatedEvent {
    private final String billingAccountId;
    private final long balance;
}
