package org.broker.application.investor.ports.output

import org.shared.domain.vo.AccountId

interface InvestorAccountEventEmitter {
    fun emitAccountCreated(id: AccountId, cpf: String)
    fun emitAccountApproved(id: AccountId)
    fun emitAccountRejected(id: AccountId)
}