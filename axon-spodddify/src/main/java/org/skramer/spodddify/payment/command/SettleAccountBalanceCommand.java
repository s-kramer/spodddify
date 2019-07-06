package org.skramer.spodddify.payment.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class SettleAccountBalanceCommand {
    @TargetAggregateIdentifier
    private final String billingAccountId;
    private final long incomingAmount;

}
