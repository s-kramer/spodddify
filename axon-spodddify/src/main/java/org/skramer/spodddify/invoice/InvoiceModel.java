package org.skramer.spodddify.invoice;

import java.time.Instant;

import org.skramer.spodddify.invoice.view.InvoiceEntity;

import lombok.Value;

@Value
class InvoiceModel {
    private String invoiceId;
    private String listenerId;
    private Instant creationTime;
    private long amount;
    private long paidAmount;

    static InvoiceModel of(InvoiceEntity entity) {
        return new InvoiceModel(entity.getInvoiceId(), entity.getListenerId(), entity.getCreationTime(), entity.getAmount(), entity.getPaidOffAmount());
    }
}
