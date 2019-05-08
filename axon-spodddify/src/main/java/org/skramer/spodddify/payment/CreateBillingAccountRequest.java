package org.skramer.spodddify.payment;

import java.io.IOException;

import org.skramer.spodddify.payment.domain.PaymentPlan;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.Value;

@Value
@JsonDeserialize(using = CreateBillingAccountRequestDeserializer.class)
class CreateBillingAccountRequest {
    private PaymentPlan paymentPlan;
}

@Component
class CreateBillingAccountRequestDeserializer extends JsonDeserializer<CreateBillingAccountRequest> {

    @Override
    public CreateBillingAccountRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final TreeNode paymentPlan = p.readValueAsTree().get("paymentPlan");
        return new CreateBillingAccountRequest(PaymentPlan.valueOf(((TextNode) paymentPlan).asText()));
    }
}
