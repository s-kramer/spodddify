package org.skramer.spodddify.invoice.event;

import java.util.UUID;

import lombok.Value;

@Value
public class CreateInvoiceCommand {

    String invoiceId;
    String billingAccountId;
    long invoiceAmount;

    public CreateInvoiceCommand(String billingAccountId, long invoiceAmount) {
        invoiceId = UUID.randomUUID().toString();
        this.billingAccountId = billingAccountId;
        this.invoiceAmount = invoiceAmount;
    }
}
