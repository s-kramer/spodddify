package org.skramer.spodddify.invoice;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class InvoiceEventHandler {

    private CommandGateway commandGateway;

    @EventHandler
    void on(BillingAccountCharged evt) {
        commandGateway.send(new CreateInvoiceCommand(evt.getAccountId(), evt.getChargeAmount()));
    }
}
