package org.skramer.spodddify.payment.command;

import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;

@Value
@Entity
public class ChargeBillingAccountCommand {
    @Id
    private final String billingAccountId;
    private final long chargeAmount;
}
