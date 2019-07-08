package org.skramer.spodddify.invoice.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class CreateInvoice {

    @TargetAggregateIdentifier
    String invoiceId;
    String listenerId;
    long invoiceAmount;

    public CreateInvoice(String listenerId, long invoiceAmount) {
        invoiceId = UUID.randomUUID().toString();
        this.listenerId = listenerId;
        this.invoiceAmount = invoiceAmount;
    }
}
