package org.skramer.spodddify.payment;

import java.util.concurrent.CompletableFuture;

import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class BillingAccountQueryHandler {
    Repository<BillingAccount> repository;

    @QueryHandler
    CompletableFuture<BillingAccount> getBillingAccount(GetBillingAccountQuery query) {
        final Aggregate<BillingAccount> result = repository.load(query.getBillingAccountId());
        CompletableFuture<BillingAccount> billingAccountCompletableFuture = new CompletableFuture<>();
        result.execute(billingAccountCompletableFuture::complete);
        return billingAccountCompletableFuture;
    }
}
