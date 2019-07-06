package org.skramer.spodddify.invoice;

import javax.persistence.Id;

import lombok.Value;

@Value
public class InvoicePaid {
    @Id
    String invoiceId;
    long payoffAmount;
}
