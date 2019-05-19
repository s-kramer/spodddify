package spodddify.billingaccount

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    request {
        method HttpMethods.HttpMethod.POST
        url $(server('/billing-account/1/charge'), client(regex('/billing-account/' + uuid() + '/charge')))
    }
    response {
        status OK()
    }
}
