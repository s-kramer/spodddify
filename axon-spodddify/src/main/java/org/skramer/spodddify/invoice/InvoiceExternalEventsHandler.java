package org.skramer.spodddify.invoice;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.skramer.spodddify.invoice.command.CreateInvoice;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.skramer.spodddify.payment.event.BillingAccountDonated;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class InvoiceExternalEventsHandler {

    private CommandGateway commandGateway;
    private UnpaidInvoicesService unpaidInvoicesService;

    @EventHandler
    void on(BillingAccountCharged evt) {
        commandGateway.send(new CreateInvoice(evt.getAccountId(), evt.getChargeAmount()));
    }

    @EventHandler
    void on(BillingAccountDonated evt) {
        unpaidInvoicesService.payUnpaidInvoices(evt);
    }

}
