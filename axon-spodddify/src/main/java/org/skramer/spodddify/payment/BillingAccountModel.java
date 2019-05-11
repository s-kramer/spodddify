package org.skramer.spodddify.payment;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
class BillingAccountModel {
    private String accountId;
    private long balance;
    private PaymentPlan paymentPlan;

    static BillingAccountModel of(BillingAccount billingAccount) {
        return new BillingAccountModel(billingAccount.getAccountId(), billingAccount.getBalance(), billingAccount.getPaymentPlan());
    }
}
