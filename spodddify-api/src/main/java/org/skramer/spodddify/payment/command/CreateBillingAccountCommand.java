package org.skramer.spodddify.payment.command;

import java.util.UUID;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateBillingAccountCommand {
    @NonNull
    private final String billingAccountId;
    @NonNull
    private final PaymentPlan paymentPlan;

    public CreateBillingAccountCommand() {
        this(PaymentPlan.FREE);
    }

    public CreateBillingAccountCommand(PaymentPlan paymentPlan) {
        billingAccountId = UUID.randomUUID().toString();
        this.paymentPlan = paymentPlan;
    }
}
