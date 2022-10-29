package org.broker.application.account.fake

import org.broker.application.account.ports.output.AccountEventEmitter
import org.shared.domain.event.account.AccountApproved
import org.shared.domain.event.account.AccountCreated
import org.shared.domain.event.account.AccountRejected
import org.shared.domain.vo.AccountId

class AccountEventEmitterMem : AccountEventEmitter {

    val events = mutableListOf<Any>()

    override fun emitAccountCreated(id: AccountId, cpf: String) {
        events += AccountCreated(id, cpf)
    }

    override fun emitAccountApproved(id: AccountId) {
        events += AccountApproved(id)
    }

    override fun emitAccountRejected(id: AccountId) {
        events += AccountRejected(id)
    }
}
