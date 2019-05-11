package org.skramer.spodddify.invoice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.axonframework.test.matchers.Matchers.andNoMore;
import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.matches;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

public class InvoiceTest {
    private static final String ACCOUNT_ID = "dummyBillingAccountId";
    private static final long INVOICE_AMOUNT = 100;
    private FixtureConfiguration<Invoice> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Invoice.class);
        fixture.registerCommandTargetResolver(new InvoiceCommandsTargetResolver());

        final CommandBus commandBus = fixture.getCommandBus();
        DefaultCommandGateway commandGateway = DefaultCommandGateway.builder().commandBus(commandBus).build();
        fixture.registerInjectableResource(new ExternalEventsHandler(commandGateway));
    }

    @Test
    public void shouldBeAbleToCreateInvoice() {
        fixture.givenNoPriorActivity()
                .when(new CreateInvoiceCommand(ACCOUNT_ID, INVOICE_AMOUNT))
                .expectEventsMatching(exactSequenceOf(invoiceWithId(), andNoMore()))
                .expectState(invoice -> {
                    assertThat(invoice.getInvoiceId()).isNotNull();
                    assertThat(invoice.getBillingAccountId()).isEqualTo(ACCOUNT_ID);
                    assertThat(invoice.getCreationTime()).isNotNull();
                    assertThat(invoice.getAmount()).isEqualTo(INVOICE_AMOUNT);
                });
    }

    private static Matcher<EventMessage<?>> invoiceWithId() {
        return matches(em -> {
            assertThat(em.getIdentifier()).isNotNull();
            assertThat(em.getPayloadType()).isEqualTo(InvoiceCreatedEvent.class);
            assertThat(((InvoiceCreatedEvent) em.getPayload()).getInvoiceId()).isNotNull();
            assertThat(((InvoiceCreatedEvent) em.getPayload()).getBillingAccountId()).isEqualTo(ACCOUNT_ID);
            assertThat(((InvoiceCreatedEvent) em.getPayload()).getCreationTime()).isNotNull();
            assertThat(((InvoiceCreatedEvent) em.getPayload()).getInvoiceAmount()).isEqualTo(INVOICE_AMOUNT);
            return true;
        });
    }

}
