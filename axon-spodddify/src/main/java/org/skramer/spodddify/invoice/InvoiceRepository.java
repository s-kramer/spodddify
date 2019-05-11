package org.skramer.spodddify.invoice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface InvoiceRepository extends CrudRepository<InvoiceEntity, String> {
    List<InvoiceEntity> findAllByBillingAccountId(String billingAccountId);
}
