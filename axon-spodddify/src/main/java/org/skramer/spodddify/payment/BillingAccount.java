package org.skramer.spodddify.payment;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.skramer.spodddify.payment.command.ChangePaymentPlan;
import org.skramer.spodddify.payment.command.ChargeBillingAccount;
import org.skramer.spodddify.payment.command.CreateBillingAccount;
import org.skramer.spodddify.payment.command.PayoffBillingAccount;
import org.skramer.spodddify.payment.domain.PaymentPlan;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.skramer.spodddify.payment.event.BillingAccountCreated;
import org.skramer.spodddify.payment.event.PaymentPlanChanged;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Aggregate
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Slf4j
class BillingAccount {
    private static final long INITIAL_ACCOUNT_BALANCE = 0L;

    @AggregateIdentifier
    private String accountId;
    private long balance;
    private PaymentPlan paymentPlan;

    @CommandHandler
    public BillingAccount(CreateBillingAccount cmd) {
        AggregateLifecycle.apply(new BillingAccountCreated(cmd.getBillingAccountId(), INITIAL_ACCOUNT_BALANCE, cmd.getPaymentPlan()));
    }

    @CommandHandler
    private void on(ChargeBillingAccount cmd) {
        AggregateLifecycle.apply(new BillingAccountCharged(accountId, paymentPlan.getFee()));
    }

    @CommandHandler
    private void on(ChangePaymentPlan cmd) {
        AggregateLifecycle.apply(new PaymentPlanChanged(cmd.getBillingAccountId(), cmd.getNewPaymentPlan()));
    }

    @CommandHandler
    private void on(PayoffBillingAccount cmd) {
        if (isPayoffNegative(cmd)) {
            throw new IllegalArgumentException("Payoff amount cannot be negative: " + cmd.getPayoffAmount());
        }
        if (isPayoffExceedingBalance(cmd)) {
            throw new IllegalArgumentException("Payoff amount " + cmd.getPayoffAmount() + " cannot exceed current balance: " + cmd.getPayoffAmount());
        }
        AggregateLifecycle.apply(new BillingAccountDonated(cmd.getBillingAccountId(), cmd.getPayoffAmount()));
    }

    private boolean isPayoffExceedingBalance(PayoffBillingAccount cmd) {
        return cmd.getPayoffAmount() + balance > 0;
    }

    private boolean isPayoffNegative(PayoffBillingAccount cmd) {
        return cmd.getPayoffAmount() <= 0;
    }

    @EventHandler
    private void on(BillingAccountCreated evt) {
        accountId = evt.getAccountId();
        balance = evt.getBalance();
        paymentPlan = evt.getPaymentPlan();
    }

    @EventHandler
    private void on(BillingAccountCharged evt) {
        log.info("Charging billing account {}: {}", accountId, evt.getChargeAmount());
        balance -= evt.getChargeAmount();
    }

    @EventHandler
    private void on(PaymentPlanChanged evt) {
        log.info("Changing plan for billing account {}: {}", accountId, evt.getNewPaymentPlan());
        paymentPlan = evt.getNewPaymentPlan();
    }

    @EventHandler
    private void on(BillingAccountDonated evt) {
        log.info("Paying off billing account {}: {}", accountId, evt.getPayoffAmount());
        balance += evt.getPayoffAmount();
    }
}
