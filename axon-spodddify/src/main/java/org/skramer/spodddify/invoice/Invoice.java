package org.skramer.spodddify.invoice;

import java.time.Instant;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.skramer.spodddify.invoice.command.CreateInvoice;
import org.skramer.spodddify.invoice.event.InvoiceCreated;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter(AccessLevel.PACKAGE)
class Invoice {
    @AggregateIdentifier
    private String invoiceId;
    private Instant creationTime;
    private long amount;
    private String billingAccountId;
    private long payoffAmount;

    @CommandHandler
    public Invoice(CreateInvoice cmd) {
        AggregateLifecycle.apply(new InvoiceCreated(cmd.getInvoiceId(), cmd.getBillingAccountId(), Instant.now(), cmd.getInvoiceAmount()));
    }

    @CommandHandler
    public void on(PayOffInvoice cmd) {
        AggregateLifecycle.apply(new InvoicePaid(cmd.getInvoiceId(), cmd.getPayOffAmount()));
    }

    @EventHandler
    public void on(InvoiceCreated evt) {
        this.invoiceId = evt.getInvoiceId();
        this.creationTime = evt.getCreationTime();
        this.amount = evt.getInvoiceAmount();
        this.billingAccountId = evt.getBillingAccountId();
    }

    @EventHandler
    public void on(InvoicePaid evt) {
        this.payoffAmount += evt.getPayoffAmount();
        if (amount == payoffAmount) {
            AggregateLifecycle.apply(new InvoicePaidCompletely(invoiceId));
        }
    }
}
