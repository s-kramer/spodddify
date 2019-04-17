package org.skramer.spodddify.payment;

import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
class BillingAccountController {
    private final QueryGateway queryGateway;

    @GetMapping("/billing-account/{billingAccountId}")
    CompletableFuture<BillingAccount> getBillingAccounts(@PathVariable("billingAccountId") String billingAccountId) {
        return queryGateway.query(new GetBillingAccountQuery(billingAccountId), ResponseTypes.instanceOf(BillingAccount.class));
    }
}
