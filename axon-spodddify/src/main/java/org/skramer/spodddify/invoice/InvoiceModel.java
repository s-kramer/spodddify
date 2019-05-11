package org.skramer.spodddify.invoice;

import java.time.Instant;

import lombok.Value;

@Value
class InvoiceModel {
    private String invoiceId;
    private String billingAccountId;
    private Instant creationTime;
    private long amount;

    static InvoiceModel of(InvoiceEntity entity) {
        return new InvoiceModel(entity.getInvoiceId(), entity.getBillingAccountId(), entity.getCreationTime(), entity.getAmount());
    }
}
