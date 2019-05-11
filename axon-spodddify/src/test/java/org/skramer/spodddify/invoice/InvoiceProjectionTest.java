package org.skramer.spodddify.invoice;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class InvoiceProjectionTest {
    private static final int INVOICE_AMOUNT = 1000;
    private static final String BILLING_ACCOUNT_ID = "billingAccountId";
    private static final String INVOICE_ID = "invoiceId";

    @Autowired
    InvoiceProjection invoiceProjection;
    private static final Instant CREATION_TIME = Instant.ofEpochSecond(1234L);

    @Test
    public void shouldBeAbleToFetchCreatedInvoices() {
        invoiceProjection.on(new InvoiceCreatedEvent(INVOICE_ID, BILLING_ACCOUNT_ID, CREATION_TIME, INVOICE_AMOUNT));
        final List<InvoiceModel> invoices = invoiceProjection.getAllInvoicesForAccount(new GetAllInvoicesForAccountQuery(BILLING_ACCOUNT_ID));

        assertThat(invoices).hasSize(1);
        assertThat(invoices.get(0)).isEqualToIgnoringGivenFields(new InvoiceModel(INVOICE_ID, BILLING_ACCOUNT_ID, CREATION_TIME, INVOICE_AMOUNT), "invoiceId");
    }
}
