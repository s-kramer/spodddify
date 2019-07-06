package org.skramer.spodddify.payment.event;


import javax.persistence.Id;

import lombok.Value;

@Value
public class BillingAccountCharged {
        @Id
        private String accountId;
        private long chargeAmount;
}
