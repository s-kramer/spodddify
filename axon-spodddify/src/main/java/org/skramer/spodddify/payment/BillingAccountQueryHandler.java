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
    private CompletableFuture<BillingAccountBalance> getBalance(BillingAccountBalanceQuery query) {
        final Aggregate<BillingAccount> result = repository.load(query.getBillingAccountId());
        final Long balance = result.invoke(BillingAccount::getBalance);
        CompletableFuture<BillingAccountBalance> billingAccountCompletableFuture = new CompletableFuture<>();
        billingAccountCompletableFuture.complete(new BillingAccountBalance(result.identifierAsString(), balance));
        return billingAccountCompletableFuture;
    }
}
