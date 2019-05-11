package org.skramer.spodddify.payment;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.skramer.spodddify.payment.command.ChangePaymentPlanCommand;
import org.skramer.spodddify.payment.command.ChargeBillingAccountCommand;
import org.skramer.spodddify.payment.command.CreateBillingAccountCommand;
import org.skramer.spodddify.payment.domain.PaymentPlan;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.skramer.spodddify.payment.event.BillingAccountCreatedEvent;
import org.skramer.spodddify.payment.event.PaymentPlanChanged;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Aggregate
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
class BillingAccount {
    private static final long INITIAL_ACCOUNT_BALANCE = 0L;

    @AggregateIdentifier
    private String accountId;
    private long balance;
    private PaymentPlan paymentPlan;

    @CommandHandler
    public BillingAccount(CreateBillingAccountCommand cmd) {
        AggregateLifecycle.apply(new BillingAccountCreatedEvent(cmd.getBillingAccountId(), INITIAL_ACCOUNT_BALANCE, cmd.getPaymentPlan()));
    }

    @CommandHandler
    private void on(ChargeBillingAccountCommand cmd) {
        AggregateLifecycle.apply(new BillingAccountCharged(accountId, paymentPlan.getFee()));
    }

    @CommandHandler
    private void on(ChangePaymentPlanCommand cmd) {
        AggregateLifecycle.apply(new PaymentPlanChanged(cmd.getBillingAccountId(), cmd.getNewPaymentPlan()));
    }

    @EventHandler
    private void on(BillingAccountCreatedEvent evt) {
        accountId = evt.getAccountId();
        balance = evt.getBalance();
        paymentPlan = evt.getPaymentPlan();
    }

    @EventHandler
    private void on(BillingAccountCharged evt) {
        balance -= evt.getChargeAmount();
    }

    @EventHandler
    private void on(PaymentPlanChanged evt) {
        paymentPlan = evt.getNewPaymentPlan();
    }
}
