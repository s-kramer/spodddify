package org.skramer.spodddify.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.skramer.spodddify.payment.event.BillingAccountCreatedEvent;

@Aggregate
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor
class BillingAccount {
    @AggregateIdentifier
    private String accountId;
    private long balance;

    @CommandHandler
    public BillingAccount(CreateBillingAccountCommand cmd) {
        AggregateLifecycle.apply(new BillingAccountCreatedEvent(cmd.getBillingAccountId(), cmd.getInitialBalance()));
    }

    @EventHandler
    public void on(BillingAccountCreatedEvent evt) {
        accountId = evt.getAccountId();
        balance = evt.getBalance();
    }

    @EventHandler
    public void on(BillingAccountCharged evt) {
        balance -= evt.getChargeAmount();
    }
}
