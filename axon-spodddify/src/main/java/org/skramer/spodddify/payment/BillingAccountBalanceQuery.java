package org.skramer.spodddify.payment;

import javax.persistence.Id;

import lombok.Value;

@Value
class BillingAccountBalanceQuery {
    @Id
    private String billingAccountId;
}
