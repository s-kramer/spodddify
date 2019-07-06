package org.skramer.spodddify.invoice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skramer.spodddify.invoice.view.InvoiceEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
class InvoicePaymentService {
    Map<String, Long> splitBillingAccountInvoicePayments(long payoffAmount, List<InvoiceEntity> unpaidInvoices) {
        log.info("Splitting {} between unpaid invoices: {}", payoffAmount, unpaidInvoices);

        Map<String, Long> result = new HashMap<>();
        long remainingAssets = payoffAmount;
        for (InvoiceEntity unpaidInvoice : unpaidInvoices) {
            final long remainingInvoicePayment = unpaidInvoice.getAmount() - unpaidInvoice.getPaidOffAmount();
            final long paymentMade = Math.min(remainingInvoicePayment, remainingAssets);
            remainingAssets -= paymentMade;
            result.put(unpaidInvoice.getInvoiceId(), paymentMade);
        }

        return result;
    }
}
