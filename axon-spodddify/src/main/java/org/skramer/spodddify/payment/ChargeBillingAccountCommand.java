package org.skramer.spodddify.payment;

import lombok.Value;
import org.axonframework.modelling.command.AggregateIdentifier;

@Value
class ChargeBillingAccountCommand {
    @AggregateIdentifier
    private final String billingAccountId;
    private final long chargeAmount;
}
