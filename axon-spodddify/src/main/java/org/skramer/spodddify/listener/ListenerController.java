package org.skramer.spodddify.listener;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.skramer.spodddify.listener.command.CreateListener;
import org.springframework.http.MediaType;
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
class ListenerController {
    private final CommandGateway commandGateway;

    @PostMapping(value = "/listener", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CompletableFuture<?> createListener(@RequestBody CreateListenerRequest createListenerRequest) {
        log.info("Creating account for request: {}", createListenerRequest);
        return commandGateway.send(new CreateListener(createListenerRequest.getPaymentPlan()))
                .thenApply(o -> (String) o)
                .thenApply(CreateListenerResponse::new);
    }


}
