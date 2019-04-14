package org.skramer.spodddify.invoice;

import java.time.Instant;

import lombok.NonNull;
import lombok.Value;

@Value
public class InvoiceCreatedEvent {
    @NonNull
    private final String invoiceId;
    @NonNull
    private final String billingAccountId;
    @NonNull
    private final Instant creationTime;
    @NonNull
    private final long invoiceAmount;
}
