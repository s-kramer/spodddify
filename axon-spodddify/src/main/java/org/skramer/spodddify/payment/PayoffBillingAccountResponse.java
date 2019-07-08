package org.skramer.spodddify.payment;

import lombok.Value;

@Value
class PayoffBillingAccountResponse {
    String listenerId;
    long remainingAmount;
}
