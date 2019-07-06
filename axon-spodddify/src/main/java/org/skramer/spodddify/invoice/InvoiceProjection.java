package org.skramer.spodddify.invoice;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.skramer.spodddify.invoice.event.GetAllInvoicesForAccountQuery;
import org.skramer.spodddify.invoice.event.InvoiceCreatedEvent;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
class InvoiceProjection {
    private InvoiceRepository invoiceRepository;

    @EventHandler
    void on(InvoiceCreatedEvent event) {
        invoiceRepository.save(new InvoiceEntity(event.getInvoiceId(), event.getCreationTime(), event.getInvoiceAmount(), event.getBillingAccountId()));
    }

    @QueryHandler
    List<InvoiceModel> getAllInvoicesForAccount(GetAllInvoicesForAccountQuery query) {
        return invoiceRepository.findAllByBillingAccountId(query.getBillingAccountId())
                .stream()
                .map(InvoiceModel::of)
                .collect(toList());
    }
}
