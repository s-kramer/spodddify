package org.skramer.spodddify.payment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Error defaultErrorHandler(IllegalArgumentException e) {
        log.info(e.toString());
        return new Error(e.getMessage());
    }

    @Value
    private static class Error {
        String message;
    }
}
