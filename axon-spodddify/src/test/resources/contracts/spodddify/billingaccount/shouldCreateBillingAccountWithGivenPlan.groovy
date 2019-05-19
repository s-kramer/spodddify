package spodddify.billingaccount

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

['FREE', 'BASIC', 'PREMIUM'].collect {
	def paymentPlan = it
	Contract.make {
		request {
			headers {
				accept(applicationJsonUtf8())
				contentType(applicationJsonUtf8())
			}

			method HttpMethods.HttpMethod.POST
			url '/billing-account'
			body([
					"paymentPlan": $(server(paymentPlan), client(regex("(FREE|BASIC|PREMIUM)")))
			])
		}
		response {
			status OK()
			body([
					billingAccountId: $(regex(uuid()))
			])
			headers {
				contentType('application/json;charset=UTF-8')
			}
		}
	}
}
