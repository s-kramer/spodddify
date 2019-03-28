package org.skramer.spodddify.payment;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.Repository;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.skramer.spodddify.payment.event.BillingAccountNotFoundEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class BillingAccountCommandHandler {
    private final Repository<BillingAccount> repository;

    @CommandHandler
    public void charge(ChargeBillingAccountCommand cmd) {
        try {
            final Aggregate<BillingAccount> aggregate = repository.load(cmd.getBillingAccountId());
            AggregateLifecycle.apply(new BillingAccountCharged(aggregate.identifierAsString(), cmd.getChargeAmount()));

        } catch (Exception e) {
            AggregateLifecycle.apply(new BillingAccountNotFoundEvent(cmd.getBillingAccountId()));
        }
    }
}
