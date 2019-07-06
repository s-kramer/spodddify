package org.skramer.spodddify.payment.command;

import lombok.Value;

@Value
public class SettleAccountBalanceCommand {
    private final String billingAccountId;
    private final long incomingAmount;

}
