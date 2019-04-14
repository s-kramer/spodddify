package org.skramer.spodddify.payment;

import lombok.Value;

@Value
public class FoundsTransferredEvent {
    private final String billingAccountId;
    private final long balanceChange;
}
