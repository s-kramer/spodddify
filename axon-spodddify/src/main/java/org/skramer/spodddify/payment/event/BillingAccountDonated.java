package org.skramer.spodddify.payment.event;

import javax.persistence.Id;

import lombok.Value;

@Value
public class BillingAccountDonated {
    @Id
    String billingAccountId;
    long payoffAmount;
}
