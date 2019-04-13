package org.skramer.spodddify.payment.command;

import lombok.Value;

@Value
public class ChargeBillingAccountCommand {
    private final String billingAccountId;
}
