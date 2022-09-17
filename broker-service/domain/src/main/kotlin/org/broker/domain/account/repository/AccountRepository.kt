package org.broker.domain.account.repository

import org.broker.domain.account.entity.Account
import org.broker.domain.account.vo.Cpf
import org.shared.domain.vo.AccountId

interface AccountRepository {
    fun save(account: Account)
    fun existsByCpf(cpf: Cpf): Boolean
    fun findById(id: AccountId): Account?
}