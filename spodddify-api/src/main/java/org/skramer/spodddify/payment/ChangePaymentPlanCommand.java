package org.skramer.spodddify.payment;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
class ChangePaymentPlanCommand {
    private final String billingAccountId;
    private final PaymentPlan newPaymentPlan;
}
