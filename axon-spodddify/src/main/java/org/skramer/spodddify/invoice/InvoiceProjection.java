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
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
class InvoiceProjection {
    private InvoiceRepository invoiceRepository;
    private static final long INITIAL_PAY_OFF_BALANCE = 0L;

    @EventHandler
    void on(InvoiceCreated evt) {
        log.info("Creating invoice projection: {}", evt);
        invoiceRepository.save(new InvoiceEntity(evt.getInvoiceId(), evt.getCreationTime(), evt.getInvoiceAmount(), evt.getListenerId(), INITIAL_PAY_OFF_BALANCE));
    }

    @EventHandler
    void on(InvoicePaid evt) {
        invoiceRepository.findById(evt.getInvoiceId())
                .map(i -> i.withPaidOffAmount((i.getPaidOffAmount() + evt.getPayoffAmount())))
                .ifPresent(invoiceRepository::save);
    }

    @QueryHandler
    List<InvoiceModel> getAllInvoicesForAccount(GetAllInvoicesForAccount query) {
        return invoiceRepository.findAllByListenerId(query.getListenerId())
                .stream()
                .map(InvoiceModel::of)
                .collect(toList());
    }
}
