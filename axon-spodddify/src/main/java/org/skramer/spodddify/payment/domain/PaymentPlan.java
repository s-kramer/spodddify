package org.skramer.spodddify.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentPlan {
    FREE(0L),
    BASIC(100L),
    PREMIUM(400L);

    long fee;
}
