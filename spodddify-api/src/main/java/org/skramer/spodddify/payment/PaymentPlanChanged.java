package org.skramer.spodddify.payment;

import javax.persistence.Id;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
class PaymentPlanChanged {
    @Id
    private final String billingAccountId;
    private final PaymentPlan newPaymentPlan;
}
