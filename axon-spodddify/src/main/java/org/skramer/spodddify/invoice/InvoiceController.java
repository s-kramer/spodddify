package org.skramer.spodddify.invoice;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.skramer.spodddify.invoice.query.GetAllInvoicesForAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
class InvoiceController {
    private final QueryGateway queryGateway;

    @GetMapping("/invoice/billingAccount/{billingAccountId}")
    CompletableFuture<List<InvoiceModel>> getInvoicesForBillingAccount(@PathVariable("billingAccountId") String billingAccountId) {
        return queryGateway.query(new GetAllInvoicesForAccount(billingAccountId), ResponseTypes.multipleInstancesOf(InvoiceModel.class));
    }

}
