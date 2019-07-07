package org.skramer.spodddify.invoice.view;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<InvoiceEntity, String> {
    List<InvoiceEntity> findAllByBillingAccountId(String billingAccountId);

    @Query("SELECT i FROM InvoiceEntity i WHERE i.paidOffAmount < i.amount ORDER BY i.creationTime")
    List<InvoiceEntity> findAllByBillingAccountIdAndPaidOffAmountNotEqualAmount(String billingAccountId);
}
