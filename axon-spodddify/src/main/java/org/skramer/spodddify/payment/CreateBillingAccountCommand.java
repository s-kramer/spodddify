package org.skramer.spodddify.payment;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
class CreateBillingAccountCommand {
    public final String billingAccountId;
    public final long initialBalance;

    CreateBillingAccountCommand(long initialBalance) {
        this(UUID.randomUUID().toString(), initialBalance);
    }
}
