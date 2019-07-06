package org.skramer.spodddify.invoice


import org.skramer.spodddify.invoice.view.InvoiceEntity
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant
import java.time.temporal.ChronoUnit

class InvoicePaymentServiceTest extends Specification {
    private static final BILLING_ACCOUNT = "billingAccount"
    private static final INVOICE_ID_1 = "invoice_1"
    private static final INVOICE_ID_2 = "invoice_2"
    private static final INVOICE_ID_3 = "invoice_3"
    private static final Instant TIME_1 = Instant.now()
    private static final Instant TIME_2 = Instant.now().plus(5, ChronoUnit.DAYS)
    private static final Instant TIME_3 = Instant.now().plus(10, ChronoUnit.DAYS)
    private static final long AMOUNT_1 = 50L
    private static final long AMOUNT_2 = 100L
    private static final long AMOUNT_3 = 200L


    private static final InvoiceEntity INVOICE_1 = new InvoiceEntity(INVOICE_ID_1, TIME_1, AMOUNT_1, BILLING_ACCOUNT, 0L)
    private static final InvoiceEntity INVOICE_2 = new InvoiceEntity(INVOICE_ID_2, TIME_2, AMOUNT_2, BILLING_ACCOUNT, 0L)
    private static final InvoiceEntity INVOICE_3 = new InvoiceEntity(INVOICE_ID_3, TIME_3, AMOUNT_3, BILLING_ACCOUNT, 0L)

    InvoicePaymentService invoicePaymentService = new InvoicePaymentService()

    @Unroll
    def "should pay off invoices #unpaidInvoices with #payoffAmount with remaining assets"(List<InvoiceEntity> unpaidInvoices,
                                                                                           long payoffAmount, Map<String, Long> expectedPayments) {
        given:

        when:
        Map<String, Long> invoicePayments = invoicePaymentService.splitBillingAccountInvoicePayments(payoffAmount, unpaidInvoices)

        then:
        invoicePayments == expectedPayments

        where:
        unpaidInvoices                    | payoffAmount || expectedPayments
        // total payments
        [INVOICE_1]                       | 50L          || [(INVOICE_ID_1): 50L]
        [INVOICE_1, INVOICE_2]            | 150L         || [(INVOICE_ID_1): 50L, (INVOICE_ID_2): 100L]
        [INVOICE_1, INVOICE_2, INVOICE_3] | 350L         || [(INVOICE_ID_1): 50L, (INVOICE_ID_2): 100L, (INVOICE_ID_3): 200L]

        // partial payments
        [INVOICE_1]                       | 30L          || [(INVOICE_ID_1): 30L]
        [INVOICE_1, INVOICE_2]            | 100L         || [(INVOICE_ID_1): 50L, (INVOICE_ID_2): 50L]
    }
}
