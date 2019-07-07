package org.skramer.spodddify.payment.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
public class ChangePaymentPlan {
    @TargetAggregateIdentifier
    private final String billingAccountId;
    private final PaymentPlan newPaymentPlan;
}
