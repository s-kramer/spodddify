package org.skramer.spodddify.invoice;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.skramer.spodddify.invoice.event.InvoiceCreated;
import org.skramer.spodddify.invoice.query.GetAllInvoicesForAccount;
import org.skramer.spodddify.invoice.view.InvoiceEntity;
import org.skramer.spodddify.invoice.view.InvoiceRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
class InvoiceProjection {
    private InvoiceRepository invoiceRepository;
    private static final long INITIAL_PAY_OFF_BALANCE = 0L;

    @EventHandler
    void on(InvoiceCreated event) {
        invoiceRepository.save(new InvoiceEntity(event.getInvoiceId(), event.getCreationTime(), event.getInvoiceAmount(), event.getBillingAccountId(), INITIAL_PAY_OFF_BALANCE));
    }

    @QueryHandler
    List<InvoiceModel> getAllInvoicesForAccount(GetAllInvoicesForAccount query) {
        return invoiceRepository.findAllByBillingAccountId(query.getBillingAccountId())
                .stream()
                .map(InvoiceModel::of)
                .collect(toList());
    }
}
