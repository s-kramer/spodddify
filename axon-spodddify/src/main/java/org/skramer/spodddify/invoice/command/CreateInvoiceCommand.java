package org.skramer.spodddify.invoice.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class CreateInvoiceCommand {

    @TargetAggregateIdentifier
    String invoiceId;
    String billingAccountId;
    long invoiceAmount;

    public CreateInvoiceCommand(String billingAccountId, long invoiceAmount) {
        invoiceId = UUID.randomUUID().toString();
        this.billingAccountId = billingAccountId;
        this.invoiceAmount = invoiceAmount;
    }
}
