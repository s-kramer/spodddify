package org.skramer.spodddify.invoice;

import lombok.Value;

@Value
class GetAllInvoicesForAccountQuery {
    private String billingAccountId;
}
