package org.skramer.spodddify.payment;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.skramer.spodddify.payment.command.ChargeBillingAccountCommand;
import org.skramer.spodddify.payment.event.BillingAccountCharged;
import org.skramer.spodddify.payment.event.BillingAccountNotFoundEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class BillingAccountCommandHandler {
    private final Repository<BillingAccount> repository;
    private EventBus eventBus;

    @CommandHandler
    public void charge(ChargeBillingAccountCommand cmd) {
        try {
            final Aggregate<BillingAccount> aggregate = repository.load(cmd.getBillingAccountId());
            eventBus.publish(
                    GenericEventMessage.asEventMessage(
                            new BillingAccountCharged(aggregate.identifierAsString(), cmd.getChargeAmount())));

        } catch (Exception e) {
            eventBus.publish(
                    GenericEventMessage.asEventMessage(
                            (new BillingAccountNotFoundEvent(cmd.getBillingAccountId()))));
        }
    }
}
