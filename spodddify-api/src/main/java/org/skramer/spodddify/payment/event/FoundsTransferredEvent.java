package org.skramer.spodddify.payment.event;

import lombok.Value;

@Value
public class FoundsTransferredEvent {
    private final String billingAccountId;
    private final long balanceChange;
}
