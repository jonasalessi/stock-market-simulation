package org.broker.application.account.ports.output

import org.broker.domain.account.entity.Account
import org.broker.domain.account.vo.Cpf
import org.shared.domain.event.account.AccountCreated
import org.shared.domain.vo.AccountId

interface AccountRepository {
    fun create(account: Account): AccountCreated
    fun update(account: Account)
    fun existsByCpf(cpf: Cpf): Boolean
    fun findById(id: AccountId): Account?
}