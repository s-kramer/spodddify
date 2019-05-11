package org.skramer.spodddify.invoice;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
class InvoiceQueryHandler {
    private InvoiceRepository invoiceRepository;

    @QueryHandler
    List<InvoiceModel> getAllInvoicesForAccount(GetAllInvoicesForAccountQuery query) {
        List<InvoiceModel> invoices = invoiceRepository.findAllByBillingAccountId(query.getBillingAccountId())
                .stream()
                .map(InvoiceModel::of)
                .collect(toList());

        return invoices;
    }
}
