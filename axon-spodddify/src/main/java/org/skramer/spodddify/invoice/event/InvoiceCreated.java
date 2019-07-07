package org.skramer.spodddify.invoice.event;

import java.time.Instant;

import lombok.NonNull;
import lombok.Value;

@Value
public class InvoiceCreated {
    @NonNull
    private final String invoiceId;
    @NonNull
    private final String billingAccountId;
    @NonNull
    private final Instant creationTime;

    private final long invoiceAmount;
}
