package org.skramer.spodddify.invoice.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class PayOffInvoice {
    @TargetAggregateIdentifier
    String invoiceId;
    long payOffAmount;
}
