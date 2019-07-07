package org.skramer.spodddify.payment.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateBillingAccount {
    @TargetAggregateIdentifier
    @NonNull
    private final String billingAccountId;
    @NonNull
    private final PaymentPlan paymentPlan;

    public CreateBillingAccount(PaymentPlan paymentPlan) {
        billingAccountId = UUID.randomUUID().toString();
        this.paymentPlan = paymentPlan;
    }
}
