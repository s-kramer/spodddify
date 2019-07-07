package org.skramer.spodddify.listener;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
class CreateListenerRequest {
    private PaymentPlan paymentPlan;
}
