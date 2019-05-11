package org.skramer.spodddify.invoice;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.modelling.command.AnnotationCommandTargetResolver;
import org.axonframework.modelling.command.CommandTargetResolver;
import org.axonframework.modelling.command.VersionedAggregateIdentifier;
import org.skramer.spodddify.payment.command.ChargeBillingAccountCommand;
import org.springframework.stereotype.Component;

// TODO: 14.04.19 skramer: create abstraction
// TODO: 11.05.19 skramer: only a single CommandTargetResolver is used, remove the other one
@Component
class InvoiceCommandsTargetResolver implements CommandTargetResolver {

    private final AnnotationCommandTargetResolver annotationCommandTargetResolver = AnnotationCommandTargetResolver.builder().build();

    @Override
    public VersionedAggregateIdentifier resolveTarget(CommandMessage<?> command) {
        if (CreateInvoiceCommand.class.isAssignableFrom(command.getPayloadType())) {
            final String invoiceId = ((CreateInvoiceCommand) command.getPayload()).getInvoiceId();
            return new VersionedAggregateIdentifier(invoiceId, null);
        }

        if (ChargeBillingAccountCommand.class.isAssignableFrom(command.getPayloadType())) {
            final String accountId = ((ChargeBillingAccountCommand) command.getPayload()).getBillingAccountId();
            return new VersionedAggregateIdentifier(accountId, null);
        }

        return annotationCommandTargetResolver.resolveTarget(command);
    }
}
