package org.skramer.spodddify.payment;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.skramer.spodddify.payment.command.CreateBillingAccountCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
class BillingAccountController {
    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    @GetMapping("/billing-account/{billingAccountId}")
    CompletableFuture<BillingAccount> getBillingAccounts(@PathVariable("billingAccountId") String billingAccountId) {
        return queryGateway.query(new GetBillingAccountQuery(billingAccountId), ResponseTypes.instanceOf(BillingAccount.class));
    }

    @PostMapping("/billing-account")
    void createBillingAccount(@RequestBody CreateBillingAccountRequest createBillingAccountRequest) {
        final CompletableFuture<Object> send = commandGateway.send(new CreateBillingAccountCommand(createBillingAccountRequest.getPaymentPlan()));
        System.out.println(send);
    }
}
