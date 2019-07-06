package org.skramer.spodddify.payment;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.skramer.spodddify.payment.command.ChargeBillingAccount;
import org.skramer.spodddify.payment.command.CreateBillingAccount;
import org.skramer.spodddify.payment.command.PayoffBillingAccount;
import org.skramer.spodddify.payment.query.BillingAccountBalanceQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
class BillingAccountController {
    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    @PostMapping(value = "/billing-account", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CompletableFuture<?> createBillingAccount(@RequestBody CreateBillingAccountRequest createBillingAccountRequest) {
        log.info("Creating account for request: {}", createBillingAccountRequest);
        return commandGateway.send(new CreateBillingAccount(createBillingAccountRequest.getPaymentPlan()))
                .thenApply(o -> (String) o)
                .thenApply(CreateBillingAccountResponse::new);
    }

    @PostMapping("/billing-account/{billingAccountId}/charge")
    CompletableFuture<BillingAccountChargedModel> chargeAccount(@PathVariable("billingAccountId") String billingAccountId) {
        log.info("charging account {}", billingAccountId);
        return commandGateway.send(new ChargeBillingAccount(billingAccountId));
    }

    @GetMapping("/billing-account/{billingAccountId}/balance")
    CompletableFuture<BillingAccountBalance> getBalance(@PathVariable("billingAccountId") String billingAccountId) {
        log.info("checking balance for account: {}", billingAccountId);
        return queryGateway.query(new BillingAccountBalanceQuery(billingAccountId), ResponseTypes.instanceOf(BillingAccountBalance.class));
    }

    @PostMapping("/billing-account/{billingAccountId}/payoff")
    void payOff(@PathVariable("billingAccountId") String billingAccountId, PayOffBillingAccountRequest payOffBillingAccountRequest) {
        log.info("Paying off balance for account: {}", billingAccountId);
        commandGateway.send(new PayoffBillingAccount(billingAccountId, payOffBillingAccountRequest.getPayoffAmount()));
    }

}
