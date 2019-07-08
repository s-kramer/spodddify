package org.skramer.spodddify.invoice.view;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceEntity {
    @Id
    private String invoiceId;
    private Instant creationTime;
    private long amount;
    private String listenerId;
    @Wither
    private long paidOffAmount;
}
