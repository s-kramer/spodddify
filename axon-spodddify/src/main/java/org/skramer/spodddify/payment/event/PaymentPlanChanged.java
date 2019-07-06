package org.skramer.spodddify.payment.event;

import javax.persistence.Id;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
public class PaymentPlanChanged {
    @Id
    private final String billingAccountId;
    private final PaymentPlan newPaymentPlan;
}
