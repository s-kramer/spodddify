package org.skramer.spodddify.payment;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.skramer.spodddify.payment.command.ChargeBillingAccount;
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

//    @PostMapping(value = "/billing-account", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    CompletableFuture<?> createBillingAccount(@RequestBody CreateBillingAccountRequest createBillingAccountRequest) {
//        log.info("Creating account for request: {}", createBillingAccountRequest);
//        return commandGateway.send(new CreateBillingAccount(createBillingAccountRequest.getPaymentPlan()))
//                .thenApply(o -> (String) o)
//                .thenApply(CreateBillingAccountResponse::new);
//    }

    @PostMapping("/billing-account/{listenerId}/charge")
    CompletableFuture<BillingAccountChargedModel> chargeAccount(@PathVariable("listenerId") String listenerId) {
        log.info("charging account {}", listenerId);
        return commandGateway.send(new ChargeBillingAccount(listenerId));
    }

    @GetMapping("/billing-account/{listenerId}/balance")
    CompletableFuture<BillingAccountBalance> getBalance(@PathVariable("listenerId") String listenerId) {
        log.info("checking balance for account: {}", listenerId);
        return queryGateway.query(new BillingAccountBalanceQuery(listenerId), ResponseTypes.instanceOf(BillingAccountBalance.class));
    }

    @PostMapping("/billing-account/{listenerId}/payoff")
    CompletableFuture<?> payOff(@PathVariable("listenerId") String listenerId, @RequestBody PayOffBillingAccountRequest payOffBillingAccountRequest) {
        log.info("Paying off balance for account: {}", listenerId);
        return commandGateway.send(new PayoffBillingAccount(listenerId, payOffBillingAccountRequest.getPayoffAmount()));
    }

}
