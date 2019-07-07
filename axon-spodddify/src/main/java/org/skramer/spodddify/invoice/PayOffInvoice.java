package org.skramer.spodddify.invoice;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
class PayOffInvoice {
    @TargetAggregateIdentifier
    String invoiceId;
    long payOffAmount;
}
