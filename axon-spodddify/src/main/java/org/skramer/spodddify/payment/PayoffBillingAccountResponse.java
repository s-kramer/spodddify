package org.skramer.spodddify.payment;

import lombok.Value;

@Value
class PayoffBillingAccountResponse {
    String billingAccountId;
    long remainingAmount;
}
