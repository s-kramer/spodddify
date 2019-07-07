package org.skramer.spodddify.invoice;

import java.util.List;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.skramer.spodddify.invoice.view.InvoiceEntity;
import org.skramer.spodddify.invoice.view.InvoiceRepository;
import org.skramer.spodddify.payment.event.BillingAccountDonated;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class UnpaidInvoicesService {
    private final InvoicePaymentService invoicePaymentService;
    private final InvoiceRepository invoiceRepository;
    private final CommandGateway commandGateway;

    void payUnpaidInvoices(BillingAccountDonated evt) {
        final List<InvoiceEntity> unpaidInvoices = invoiceRepository.findAllByBillingAccountIdAndPaidOffAmountNotEqualAmount(evt.getBillingAccountId());
        log.debug("Fetched unpaid invoices for billing account {}: {}", evt.getBillingAccountId(), unpaidInvoices);

        final Map<String, Long> invoicePayments = invoicePaymentService.splitBillingAccountInvoicePayments(evt.getPayoffAmount(), unpaidInvoices);
        log.debug("Split amount {} between unpaid invoices: {}", evt.getPayoffAmount(), invoicePayments);

        for (Map.Entry<String, Long> payment : invoicePayments.entrySet()) {
            commandGateway.send(new PayOffInvoice(payment.getKey(), payment.getValue()));
        }
    }
}
