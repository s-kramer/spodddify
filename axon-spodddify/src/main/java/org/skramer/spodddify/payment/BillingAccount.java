package org.skramer.spodddify.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.skramer.spodddify.payment.command.CreateBillingAccountCommand;
import org.skramer.spodddify.payment.domain.PaymentPlan;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.skramer.spodddify.payment.event.BillingAccountCreatedEvent;

@Aggregate
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor
public class BillingAccount {
    private static final long INITIAL_ACCOUNT_BALANCE = 0L;

    @AggregateIdentifier
    private String accountId;
    private long balance;
    private PaymentPlan paymentPlan;

    @CommandHandler
    public BillingAccount(CreateBillingAccountCommand cmd) {
        AggregateLifecycle.apply(new BillingAccountCreatedEvent(cmd.getBillingAccountId(), INITIAL_ACCOUNT_BALANCE, cmd.getPaymentPlan()));
    }

    @EventHandler
    public void on(BillingAccountCreatedEvent evt) {
        accountId = evt.getAccountId();
        balance = evt.getBalance();
        paymentPlan = evt.getPaymentPlan();
    }

    @EventHandler
    public void on(BillingAccountCharged evt) {
        balance -= evt.getChargeAmount();
    }
}
