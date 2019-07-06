package org.skramer.spodddify.payment.command;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
public class ChangePaymentPlanCommand {
    private final String billingAccountId;
    private final PaymentPlan newPaymentPlan;
}
