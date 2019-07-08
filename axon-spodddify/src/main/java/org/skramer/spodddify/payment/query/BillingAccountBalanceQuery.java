package org.skramer.spodddify.payment.query;

import javax.persistence.Id;

import lombok.Value;

@Value
public class BillingAccountBalanceQuery {
    @Id
    private String listenerId;
}
