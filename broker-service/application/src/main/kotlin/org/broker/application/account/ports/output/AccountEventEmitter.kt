package org.broker.application.account.ports.output

import org.shared.domain.vo.AccountId

interface AccountEventEmitter {
    fun emitAccountCreated(id: AccountId, cpf: String)
    fun emitAccountApproved(id: AccountId)
    fun emitAccountRejected(id: AccountId)
}