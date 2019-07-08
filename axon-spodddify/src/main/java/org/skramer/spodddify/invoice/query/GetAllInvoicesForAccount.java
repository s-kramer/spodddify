package org.skramer.spodddify.invoice.query;

import lombok.Value;

@Value
public class GetAllInvoicesForAccount {
    private String listenerId;
}
