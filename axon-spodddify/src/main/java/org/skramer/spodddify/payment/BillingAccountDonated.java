package org.skramer.spodddify.payment;

import javax.persistence.Id;

import lombok.Value;

@Value
public class BillingAccountDonated {
    @Id
    String billingAccountId;
    long payoffAmount;
}
