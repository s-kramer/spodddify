package org.skramer.spodddify.invoice.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class CreateInvoice {

    @TargetAggregateIdentifier
    String invoiceId;
    String billingAccountId;
    long invoiceAmount;

    public CreateInvoice(String billingAccountId, long invoiceAmount) {
        invoiceId = UUID.randomUUID().toString();
        this.billingAccountId = billingAccountId;
        this.invoiceAmount = invoiceAmount;
    }
}
