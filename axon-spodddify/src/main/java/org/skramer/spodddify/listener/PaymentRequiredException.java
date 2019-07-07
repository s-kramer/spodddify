package org.skramer.spodddify.listener;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.RequiredArgsConstructor;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
@RequiredArgsConstructor
class PaymentRequiredException extends RuntimeException {
    private final String listenerId;
}
