package org.skramer.spodddify.payment;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
class BillingAccount {
    @AggregateIdentifier
    private String accoundId;
    private long balance;

    public BillingAccount() {
    }

    @CommandHandler
    public BillingAccount(CreateBillingAccountCommand cmd) {
        AggregateLifecycle.apply(new BillingAccountCreatedEvent(cmd.getBillingAccountId(), cmd.getInitialBalance()));
    }

    @EventHandler
    public void on(BillingAccountCreatedEvent evt) {
        accoundId = evt.getBillingAccountId();
        balance = evt.getBalance();
    }

//    void charge(long chargeAmount) {
//    }
}
