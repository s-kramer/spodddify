package org.skramer.spodddify.payment;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class BillingAccountTest {
    private static final String BILLING_ACCOUNT_ID = "billingAccountId";
    private static final long INITIAL_BALANCE = 100L;
    private static final long CHARGE_AMOUNT = 10L;
    private FixtureConfiguration<BillingAccount> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(BillingAccount.class);
    }

    @Test
    public void shouldCreateBillingAccountWithInitialBalance() {
        fixture.givenNoPriorActivity()
                .when(new CreateBillingAccountCommand(BILLING_ACCOUNT_ID, INITIAL_BALANCE))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new BillingAccountCreatedEvent(BILLING_ACCOUNT_ID, INITIAL_BALANCE));
    }

    @Test
    public void shouldCreateBillingAccountWithInitialBalanceWithAutogeneratedAccountId() throws NoSuchFieldException {
        fixture.givenNoPriorActivity()
                .when(new CreateBillingAccountCommand(INITIAL_BALANCE))
                .expectSuccessfulHandlerExecution();
    }

    @Test
    public void shouldGenerateChargeForBillingAccount() {
        fixture.givenNoPriorActivity()
                .when(new ChargeBillingAccountCommand(BILLING_ACCOUNT_ID, CHARGE_AMOUNT))
//                .expectException(IllegalStateException.class);
                .expectEvents(new BillingAccountNotFoundEvent(BILLING_ACCOUNT_ID));
    }
}