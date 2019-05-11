package org.skramer.spodddify.payment;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.skramer.spodddify.payment.command.ChargeBillingAccountCommand;
import org.skramer.spodddify.payment.command.CreateBillingAccountCommand;
import org.skramer.spodddify.payment.query.BillingAccountBalanceQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
class BillingAccountController {
    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    @PostMapping("/billing-account")
    CompletableFuture<Object> createBillingAccount(@RequestBody CreateBillingAccountRequest createBillingAccountRequest) {
        return commandGateway.send(new CreateBillingAccountCommand(createBillingAccountRequest.getPaymentPlan()));
    }

    @PostMapping("/billing-account/{billingAccountId}/charge")
    CompletableFuture<BillingAccountChargedModel> chargeAccount(@PathVariable("billingAccountId") String billingAccountId) {
        return commandGateway.send(new ChargeBillingAccountCommand(billingAccountId));
    }

    @GetMapping("/billing-account/{billingAccountId}/balance")
    CompletableFuture<BillingAccountBalance> getBalance(@PathVariable("billingAccountId") String billingAccountId) {
        return queryGateway.query(new BillingAccountBalanceQuery(billingAccountId), ResponseTypes.instanceOf(BillingAccountBalance.class));
    }

}
