package org.skramer.spodddify.payment.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class PayoffBillingAccount {
    @TargetAggregateIdentifier
    String billingAccountId;
    long payoffAmount;
}
