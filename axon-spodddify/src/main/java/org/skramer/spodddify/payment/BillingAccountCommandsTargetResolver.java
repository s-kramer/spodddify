package org.skramer.spodddify.payment;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.modelling.command.AnnotationCommandTargetResolver;
import org.axonframework.modelling.command.CommandTargetResolver;
import org.axonframework.modelling.command.VersionedAggregateIdentifier;
import org.skramer.spodddify.payment.command.ChargeBillingAccountCommand;
import org.springframework.stereotype.Component;

@Component
class BillingAccountCommandsTargetResolver implements CommandTargetResolver {

    private final AnnotationCommandTargetResolver annotationCommandTargetResolver = AnnotationCommandTargetResolver.builder().build();

    @Override
    public VersionedAggregateIdentifier resolveTarget(CommandMessage<?> command) {
        if (ChargeBillingAccountCommand.class.isAssignableFrom(command.getPayloadType())) {
            final String billingAccountId = ((ChargeBillingAccountCommand) command.getPayload()).getBillingAccountId();
            return new VersionedAggregateIdentifier(billingAccountId, null);
        }
        if (ChangePaymentPlanCommand.class.isAssignableFrom(command.getPayloadType())) {
            final String billingAccountId = ((ChangePaymentPlanCommand) command.getPayload()).getBillingAccountId();
            return new VersionedAggregateIdentifier(billingAccountId, null);
        }

        return annotationCommandTargetResolver.resolveTarget(command);
    }
}
