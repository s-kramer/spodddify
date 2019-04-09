package org.skramer.spodddify.payment;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.skramer.spodddify.payment.event.BillingAccountCreatedEvent;
import org.skramer.spodddify.payment.event.BillingAccountNotFoundEvent;

import static org.assertj.core.api.Assertions.assertThat;

public class BillingAccountTest {
    private static final String BILLING_ACCOUNT_ID = "accountId";
    private static final long INITIAL_BALANCE = 100L;
    private static final long CHARGE_AMOUNT = 10L;
    private FixtureConfiguration<BillingAccount> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(BillingAccount.class);
        fixture.registerAnnotatedCommandHandler(
                new BillingAccountCommandHandler(fixture.getRepository(), fixture.getEventBus()));
    }

    @Test
    public void shouldCreateBillingAccountWithInitialBalance() {
        fixture.givenNoPriorActivity()
                .when(new CreateBillingAccountCommand(BILLING_ACCOUNT_ID, INITIAL_BALANCE))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new BillingAccountCreatedEvent(BILLING_ACCOUNT_ID, INITIAL_BALANCE));
    }

    @Test
    public void shouldCreateBillingAccountWithInitialBalanceWithAutogeneratedAccountId() {
        fixture.givenNoPriorActivity()
                .when(new CreateBillingAccountCommand(INITIAL_BALANCE))
                .expectSuccessfulHandlerExecution()
                .expectState(ba -> {
                    assertThat(ba.getBalance()).isEqualTo(INITIAL_BALANCE);
                    assertThat(ba.getAccountId()).isNotEmpty();
                });
    }

    @Test
    public void shouldThrowExceptionWhenChargingInexistingAccount() {
        fixture.givenNoPriorActivity()
                .when(new ChargeBillingAccountCommand(BILLING_ACCOUNT_ID, CHARGE_AMOUNT))
                .expectEvents(new BillingAccountNotFoundEvent(BILLING_ACCOUNT_ID));
    }
}