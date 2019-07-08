package org.skramer.spodddify.payment;

import java.util.concurrent.CompletableFuture;

import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.skramer.spodddify.payment.query.BillingAccountBalanceQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
class BillingAccountQueryHandler {
    private Repository<BillingAccount> repository;

    public BillingAccountQueryHandler(@Qualifier("billingAccountRepository") Repository<BillingAccount> repository) {
        this.repository = repository;
    }

    @QueryHandler
    private CompletableFuture<BillingAccountBalance> getBalance(BillingAccountBalanceQuery query) {
        final Aggregate<BillingAccount> result = repository.load(query.getListenerId());
        final Long balance = result.invoke(BillingAccount::getBalance);
        CompletableFuture<BillingAccountBalance> billingAccountCompletableFuture = new CompletableFuture<>();
        billingAccountCompletableFuture.complete(new BillingAccountBalance(result.identifierAsString(), balance));
        return billingAccountCompletableFuture;
    }
}
