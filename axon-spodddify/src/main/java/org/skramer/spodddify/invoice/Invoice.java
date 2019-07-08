package org.skramer.spodddify.invoice;

import java.time.Instant;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.skramer.spodddify.invoice.command.CreateInvoice;
import org.skramer.spodddify.invoice.command.PayOffInvoice;
import org.skramer.spodddify.invoice.event.InvoiceCreated;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter(AccessLevel.PACKAGE)
@Slf4j
class Invoice {
    @AggregateIdentifier
    private String invoiceId;
    private Instant creationTime;
    private long amount;
    private String listenerId;
    private long payoffAmount;

    @CommandHandler
    public Invoice(CreateInvoice cmd) {
        AggregateLifecycle.apply(new InvoiceCreated(cmd.getInvoiceId(), cmd.getListenerId(), Instant.now(), cmd.getInvoiceAmount()));
    }

    @CommandHandler
    public void on(PayOffInvoice cmd) {
        log.info("Invoice pay off requested: {}", cmd);
        AggregateLifecycle.apply(new InvoicePaid(cmd.getInvoiceId(), cmd.getPayOffAmount()));
    }

    @EventHandler
    public void on(InvoiceCreated evt) {
        log.info("Creating invoice: {}", evt);
        this.invoiceId = evt.getInvoiceId();
        this.creationTime = evt.getCreationTime();
        this.amount = evt.getInvoiceAmount();
        this.listenerId = evt.getListenerId();
    }

    @EventHandler
    public void on(InvoicePaid evt) {
        log.info("Paying invoice: {}", evt);
        this.payoffAmount += evt.getPayoffAmount();
        if (amount == payoffAmount) {
            AggregateLifecycle.apply(new InvoicePaidCompletely(invoiceId));
        }
    }
}
