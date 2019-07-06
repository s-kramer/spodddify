package org.skramer.spodddify.invoice.event;

import lombok.Value;

@Value
public class GetAllInvoicesForAccount {
    private String billingAccountId;
}
