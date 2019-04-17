package org.skramer.spodddify.invoice;

import java.time.Instant;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateRoot;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AggregateRoot
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter(AccessLevel.PACKAGE)
class Invoice {
    private static final long INITIAL_BALANCE = 0L;

    @AggregateIdentifier
    private String invoiceId;
    private Instant creationTime;
    private long amount;
    private String billingAccountId;

    @CommandHandler
    public Invoice(CreateInvoiceCommand cmd) {
        AggregateLifecycle.apply(new InvoiceCreatedEvent(cmd.getInvoiceId(), cmd.getBillingAccountId(), Instant.now(), cmd.getInvoiceAmount()));
    }

    @EventHandler
    public void on(InvoiceCreatedEvent evt) {
        this.invoiceId = evt.getInvoiceId();
        this.creationTime = evt.getCreationTime();
        this.amount = evt.getInvoiceAmount();
        this.billingAccountId = evt.getBillingAccountId();
    }
}
