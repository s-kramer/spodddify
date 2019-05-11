package org.skramer.spodddify.payment.command;

import javax.persistence.Id;

import lombok.Value;

@Value
public class ChargeBillingAccountCommand {
    @Id
    private final String billingAccountId;
}
