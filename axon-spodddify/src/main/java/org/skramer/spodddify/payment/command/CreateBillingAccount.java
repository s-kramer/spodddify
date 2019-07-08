package org.skramer.spodddify.payment.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateBillingAccount {
    @TargetAggregateIdentifier
    @NonNull
    private final String listenerId;
    @NonNull
    private final PaymentPlan paymentPlan;

    public CreateBillingAccount(String listenerId, PaymentPlan paymentPlan) {
        this.listenerId = listenerId;
        this.paymentPlan = paymentPlan;
    }
}
