package org.skramer.spodddify.invoice.event;

import lombok.Value;

@Value
public class GetAllInvoicesForAccountQuery {
    private String billingAccountId;
}
