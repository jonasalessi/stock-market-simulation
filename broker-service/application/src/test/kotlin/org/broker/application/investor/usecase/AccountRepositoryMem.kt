package org.broker.application.investor.usecase

import org.broker.domain.account.entity.Account
import org.broker.domain.account.repository.AccountRepository
import org.broker.domain.account.vo.Cpf
import org.shared.domain.vo.AccountId

class AccountRepositoryMem : AccountRepository {
    val data = mutableListOf<Account>()

    override fun save(account: Account) {
        data += account
    }

    override fun existsByCpf(cpf: Cpf) = data.any { it.investor.cpf == cpf }
    override fun findById(id: AccountId) = data.find { it.id == id }
}
