package org.skramer.spodddify.payment.event;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
public class PaymentPlanChanged {
    private final String billingAccountId;
    private final PaymentPlan newPaymentPlan;
}
