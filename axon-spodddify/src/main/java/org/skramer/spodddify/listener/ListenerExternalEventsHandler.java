package org.skramer.spodddify.listener;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.skramer.spodddify.listener.event.ListenerCreated;
import org.skramer.spodddify.payment.command.CreateBillingAccount;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class ListenerExternalEventsHandler {

    private CommandGateway commandGateway;

    @EventHandler
    void on(ListenerCreated evt) {
        commandGateway.send(new CreateBillingAccount(evt.getListenerId(), evt.getPaymentPlan()));
    }

}

