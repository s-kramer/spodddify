package org.skramer.spodddify.listener;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.skramer.spodddify.listener.event.ListenerCreated;
import org.skramer.spodddify.payment.command.CreateBillingAccount;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class ExternalEventsHandler {

    private CommandGateway commandGateway;

    @EventHandler
    void on(ListenerCreated evt) {
        // TODO: emit this command from Listener's constructor to obtain billingAccountId? Or merge the IDs?
        commandGateway.send(new CreateBillingAccount(evt.getListenerId(), evt.getPaymentPlan()));
    }

}

