package org.broker.application.account.ports.output

import org.shared.domain.event.account.AccountApproved
import org.shared.domain.event.account.AccountCreated
import org.shared.domain.event.account.AccountRejected

interface AccountAnalysisPublisher {
    fun pub(accountApproved: AccountApproved)
    fun pub(accountRejected: AccountRejected)
}
interface AccountCreatePublisher {
    fun pub(accountCreated: AccountCreated)
}