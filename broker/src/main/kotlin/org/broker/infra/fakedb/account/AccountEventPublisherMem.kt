package org.broker.infra.fakedb.account

import org.broker.application.account.ports.output.AccountAnalyzerPublisher
import org.broker.application.account.ports.output.AccountCreatorPublisher
import org.shared.domain.event.account.AccountApproved
import org.shared.domain.event.account.AccountCreated
import org.shared.domain.event.account.AccountRejected
import org.springframework.stereotype.Component

@Component
class AccountEventPublisherMem : AccountCreatorPublisher, AccountAnalyzerPublisher {

    val events = mutableListOf<Any>()

    override fun pub(accountCreated: AccountCreated) {
        events += accountCreated
    }

    override fun pub(accountApproved: AccountApproved) {
        events += accountApproved
    }

    override fun pub(accountRejected: AccountRejected) {
        events += accountRejected
    }


}
