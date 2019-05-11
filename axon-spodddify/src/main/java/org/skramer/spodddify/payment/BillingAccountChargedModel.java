package org.skramer.spodddify.payment;

import org.skramer.spodddify.payment.event.BillingAccountCharged;

import lombok.Value;

@Value
class BillingAccountChargedModel {
    private String accountId;
    private long chargeAmount;

    static BillingAccountChargedModel of(BillingAccountCharged billingAccountCharged) {
        return new BillingAccountChargedModel(billingAccountCharged.getAccountId(), billingAccountCharged.getChargeAmount());
    }
}
