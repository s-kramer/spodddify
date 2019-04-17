package org.skramer.spodddify.payment;


import lombok.Value;

@Value
class GetBillingAccountQuery {
    String billingAccountId;
}
