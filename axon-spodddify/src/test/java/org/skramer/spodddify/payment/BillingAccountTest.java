package org.skramer.spodddify.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.axonframework.test.matchers.Matchers.andNoMore;
import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.matches;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.skramer.spodddify.payment.command.ChargeBillingAccountCommand;
import org.skramer.spodddify.payment.command.CreateBillingAccountCommand;
import org.skramer.spodddify.payment.domain.PaymentPlan;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.skramer.spodddify.payment.event.BillingAccountCreatedEvent;

public class BillingAccountTest {
    private static final String DUMMY_BILLING_ACCOUNT_ID = "dummyAccountId";
    private static final long INITIAL_BALANCE = 0L;
    private FixtureConfiguration<BillingAccount> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(BillingAccount.class);
        fixture.registerCommandTargetResolver(new BillingAccountCommandsTargetResolver());
    }

    @Test
    public void shouldCreateBillingAccountWithDefaultPlan() {
        fixture.givenNoPriorActivity()
                .when(new CreateBillingAccountCommand())
                .expectEventsMatching(
                        exactSequenceOf(
                                accountWithInitialBalanceAndPlan(PaymentPlan.FREE),
                                andNoMore()
                        )
                );
    }

    private static Matcher<EventMessage<?>> accountWithInitialBalanceAndPlan(PaymentPlan paymentPlan) {
        return matches(em -> {
            assertThat(em.getIdentifier()).isNotNull();
            assertThat(em.getPayloadType()).isEqualTo(BillingAccountCreatedEvent.class);
            assertThat(em.getPayload()).isEqualToIgnoringGivenFields(new BillingAccountCreatedEvent(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, paymentPlan), "accountId");
            return true;
        });
    }

    @Test
    public void shouldCreateBillingAccountWithSpecifiedPlan() {
        fixture.givenNoPriorActivity()
                .when(new CreateBillingAccountCommand(PaymentPlan.PREMIUM))
                .expectEventsMatching(
                        exactSequenceOf(
                                accountWithInitialBalanceAndPlan(PaymentPlan.PREMIUM),
                                andNoMore()
                        )
                );
    }

    @Test
    public void shouldNotChargeBillingAccountOnFreePaymentPlan() {
        assertFeeIsDeductedAccordingToPaymentPlan(PaymentPlan.FREE, -PaymentPlan.FREE.getFee());
        assertFeeIsDeductedAccordingToPaymentPlan(PaymentPlan.BASIC, -PaymentPlan.BASIC.getFee());
        assertFeeIsDeductedAccordingToPaymentPlan(PaymentPlan.PREMIUM, -PaymentPlan.PREMIUM.getFee());
    }

    private void assertFeeIsDeductedAccordingToPaymentPlan(PaymentPlan paymentPlan, long endBalance) {
        fixture.given(new BillingAccountCreatedEvent(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, paymentPlan))
                .when(new ChargeBillingAccountCommand(DUMMY_BILLING_ACCOUNT_ID))
                .expectEvents(new BillingAccountCharged(DUMMY_BILLING_ACCOUNT_ID, paymentPlan.getFee()))
                .expectState(ba -> assertThat(ba.getBalance()).isEqualTo(endBalance));
    }

    @Test
    public void shouldBeAbleToChangePaymentPlan() {
        fixture.given(new BillingAccountCreatedEvent(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, PaymentPlan.FREE))
                .when(new ChangePaymentPlanCommand(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.BASIC))
                .expectState(ba -> assertThat(ba.getPaymentPlan()).isEqualTo(PaymentPlan.BASIC));
    }

    @Test
    public void shouldChargeNewFeeAfterPlanIsChanged() {
        fixture.given(new BillingAccountCreatedEvent(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, PaymentPlan.FREE))
                .andGiven(new PaymentPlanChanged(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.PREMIUM))
                .when(new ChargeBillingAccountCommand(DUMMY_BILLING_ACCOUNT_ID))
                .expectEvents(new BillingAccountCharged(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.PREMIUM.getFee()))
                .expectState(ba -> assertThat(ba.getBalance()).isEqualTo(-PaymentPlan.PREMIUM.getFee()));
    }


    @Test
    public void shouldBeAbleToChargeMultipleTimes() {
        fixture.given(new BillingAccountCreatedEvent(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, PaymentPlan.BASIC))
                .andGivenCommands(new ChargeBillingAccountCommand(DUMMY_BILLING_ACCOUNT_ID))
                .andGivenCommands(new ChangePaymentPlanCommand(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.PREMIUM))
                .andGiven(new BillingAccountCharged(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.FREE.getFee()))
                .when(new ChargeBillingAccountCommand(DUMMY_BILLING_ACCOUNT_ID))
                .expectState(ba -> assertThat(ba.getBalance()).isEqualTo(-PaymentPlan.BASIC.getFee() - PaymentPlan.PREMIUM.getFee()));
    }

//    @Test
//    public void shouldCreateBillingAccountWithInitialBalance() {
//        fixture.givenNoPriorActivity()
//                .when(new CreateBillingAccountCommand(BILLING_ACCOUNT_ID, INITIAL_BALANCE))
//                .expectSuccessfulHandlerExecution()
//                .expectEvents(new BillingAccountCreatedEvent(BILLING_ACCOUNT_ID, INITIAL_BALANCE, PaymentPlan.FREE));
//    }
//
//    @Test
//    public void shouldCreateBillingAccountWithInitialBalanceWithAutogeneratedAccountId() {
//        fixture.givenNoPriorActivity()
//                .when(new CreateBillingAccountCommand())
//                .expectSuccessfulHandlerExecution()
//                .expectState(ba -> {
//                    assertThat(ba.getBalance()).isEqualTo(INITIAL_BALANCE);
//                    assertThat(ba.getAccountId()).isNotEmpty();
//                });
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenChargingInexistingAccount() {
//        fixture.givenNoPriorActivity()
//                .when(new ChargeBillingAccountCommand(BILLING_ACCOUNT_ID, CHARGE_AMOUNT))
//                .expectEvents(new BillingAccountNotFoundEvent(BILLING_ACCOUNT_ID));
//    }
}