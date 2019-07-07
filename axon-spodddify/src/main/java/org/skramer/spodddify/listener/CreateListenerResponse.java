package org.skramer.spodddify.listener;

import lombok.Value;

@Value
class CreateListenerResponse {
    // TODO: access billing account through listenerId?
    String listenerId;
    String billingAccountId;
}
