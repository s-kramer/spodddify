package org.skramer.spodddify.payment;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
class CreateBillingAccountRequest {
    private PaymentPlan paymentPlan;
}
