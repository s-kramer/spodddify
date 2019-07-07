package org.skramer.spodddify.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.axonframework.test.matchers.Matchers.andNoMore;
import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.matches;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.skramer.spodddify.payment.command.ChangePaymentPlan;
import org.skramer.spodddify.payment.command.ChargeBillingAccount;
import org.skramer.spodddify.payment.command.CreateBillingAccount;
import org.skramer.spodddify.payment.domain.PaymentPlan;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.skramer.spodddify.payment.event.BillingAccountCreated;
import org.skramer.spodddify.payment.event.PaymentPlanChanged;

public class BillingAccountTest {
    private static final String DUMMY_BILLING_ACCOUNT_ID = "dummyAccountId";
    private static final long INITIAL_BALANCE = 0L;
    private FixtureConfiguration<BillingAccount> fixture = new AggregateTestFixture<>(BillingAccount.class);

    private static Matcher<EventMessage<?>> accountWithInitialBalanceAndPlan(PaymentPlan paymentPlan) {
        return matches(em -> {
            assertThat(em.getIdentifier()).isNotNull();
            assertThat(em.getPayloadType()).isEqualTo(BillingAccountCreated.class);
            assertThat(em.getPayload()).isEqualToIgnoringGivenFields(new BillingAccountCreated(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, paymentPlan), "accountId");
            return true;
        });
    }

    @Test
    public void shouldCreateBillingAccountWithSpecifiedPlan() {
        fixture.givenNoPriorActivity()
                .when(new CreateBillingAccount(PaymentPlan.PREMIUM))
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
        fixture.given(new BillingAccountCreated(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, paymentPlan))
                .when(new ChargeBillingAccount(DUMMY_BILLING_ACCOUNT_ID))
                .expectEvents(new BillingAccountCharged(DUMMY_BILLING_ACCOUNT_ID, paymentPlan.getFee()))
                .expectState(ba -> assertThat(ba.getBalance()).isEqualTo(endBalance));
    }

    @Test
    public void shouldBeAbleToChangePaymentPlan() {
        fixture.given(new BillingAccountCreated(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, PaymentPlan.FREE))
                .when(new ChangePaymentPlan(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.BASIC))
                .expectState(ba -> assertThat(ba.getPaymentPlan()).isEqualTo(PaymentPlan.BASIC));
    }

    @Test
    public void shouldChargeNewFeeAfterPlanIsChanged() {
        fixture.given(new BillingAccountCreated(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, PaymentPlan.FREE))
                .andGiven(new PaymentPlanChanged(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.PREMIUM))
                .when(new ChargeBillingAccount(DUMMY_BILLING_ACCOUNT_ID))
                .expectEvents(new BillingAccountCharged(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.PREMIUM.getFee()))
                .expectState(ba -> assertThat(ba.getBalance()).isEqualTo(-PaymentPlan.PREMIUM.getFee()));
    }


    @Test
    public void shouldBeAbleToChargeMultipleTimes() {
        fixture.given(new BillingAccountCreated(DUMMY_BILLING_ACCOUNT_ID, INITIAL_BALANCE, PaymentPlan.BASIC))
                .andGivenCommands(new ChargeBillingAccount(DUMMY_BILLING_ACCOUNT_ID))
                .andGivenCommands(new ChangePaymentPlan(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.PREMIUM))
                .andGiven(new BillingAccountCharged(DUMMY_BILLING_ACCOUNT_ID, PaymentPlan.FREE.getFee()))
                .when(new ChargeBillingAccount(DUMMY_BILLING_ACCOUNT_ID))
                .expectState(ba -> assertThat(ba.getBalance()).isEqualTo(-PaymentPlan.BASIC.getFee() - PaymentPlan.PREMIUM.getFee()));
    }
}