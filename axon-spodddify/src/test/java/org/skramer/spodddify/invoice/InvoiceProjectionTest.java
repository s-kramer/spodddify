package org.skramer.spodddify.invoice;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skramer.spodddify.invoice.event.InvoiceCreated;
import org.skramer.spodddify.invoice.query.GetAllInvoicesForAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class InvoiceProjectionTest {
    private static final int INVOICE_AMOUNT = 1000;
    private static final String BILLING_ACCOUNT_ID = "listenerId";
    private static final String INVOICE_ID = "invoiceId";
    private static final long INITIAL_PAYOFF_AMOUNT = 0L;

    @Autowired
    InvoiceProjection invoiceProjection;
    private static final Instant CREATION_TIME = Instant.ofEpochSecond(1234L);

    @Test
    public void shouldBeAbleToFetchCreatedInvoices() {
        invoiceProjection.on(new InvoiceCreated(INVOICE_ID, BILLING_ACCOUNT_ID, CREATION_TIME, INVOICE_AMOUNT));
        final List<InvoiceModel> invoices = invoiceProjection.getAllInvoicesForAccount(new GetAllInvoicesForAccount(BILLING_ACCOUNT_ID));

        assertThat(invoices).hasSize(1);
        assertThat(invoices.get(0)).isEqualToIgnoringGivenFields(new InvoiceModel(INVOICE_ID, BILLING_ACCOUNT_ID, CREATION_TIME, INVOICE_AMOUNT, INITIAL_PAYOFF_AMOUNT), "invoiceId");
    }
}
