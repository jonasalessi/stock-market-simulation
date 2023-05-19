package org.broker.application.account.ports.output

import org.shared.domain.event.account.AccountApproved
import org.shared.domain.event.account.AccountCreated
import org.shared.domain.event.account.AccountRejected

interface AccountAnalyzerPublisher {
    fun pub(accountApproved: AccountApproved)
    fun pub(accountRejected: AccountRejected)
}

interface AccountCreatorPublisher {
    fun pub(accountCreated: AccountCreated)
}