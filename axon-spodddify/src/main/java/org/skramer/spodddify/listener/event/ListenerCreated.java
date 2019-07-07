package org.skramer.spodddify.listener.event;

import org.skramer.spodddify.listener.ListenerStatus;
import org.skramer.spodddify.payment.domain.PaymentPlan;

import lombok.Value;

@Value
public class ListenerCreated {
    String listenerId;
    ListenerStatus listenerStatus;
    // TODO: is it ok to put this enum from other service here?
    PaymentPlan paymentPlan;
}
