package org.skramer.spodddify.payment.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class ChargeBillingAccount {
    @TargetAggregateIdentifier
    private final String billingAccountId;
}
