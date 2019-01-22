package org.skramer.spodddify.payment;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class BillingAccountCommandHandler {
    private final Repository<BillingAccount> repository;

    @CommandHandler
    public void charge(ChargeBillingAccountCommand cmd) {
        try {
            final Aggregate<BillingAccount> aggregate = repository.load(cmd.getBillingAccountId());
//            aggregate.execute(ba -> ba.charge(cmd.getChargeAmount()));

        } catch (Exception e) {
            AggregateLifecycle.apply(new BillingAccountNotFoundEvent(cmd.getBillingAccountId()));
        }
    }
}
