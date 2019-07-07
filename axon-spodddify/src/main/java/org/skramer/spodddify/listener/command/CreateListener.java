package org.skramer.spodddify.listener.command;

import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
public class CreateListener {
    PaymentPlan paymentPlan;
}
