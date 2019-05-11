package org.skramer.spodddify.invoice;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
class RepositoryUpdatingInvoiceEventHandler {
    private InvoiceRepository invoiceRepository;

    @EventHandler
    void on(InvoiceCreatedEvent event) {
        invoiceRepository.save(new InvoiceEntity(event.getInvoiceId(), event.getCreationTime(), event.getInvoiceAmount(), event.getBillingAccountId()));
    }
}
