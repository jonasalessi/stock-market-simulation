package org.broker.application.account.fake

import org.broker.application.account.ports.output.AccountRepository
import org.broker.domain.account.entity.Account
import org.broker.domain.account.vo.Cpf
import org.shared.domain.event.account.AccountCreated
import org.shared.domain.vo.AccountId

class AccountRepositoryMem : AccountRepository {
    val data = mutableMapOf<AccountId, Account>()

    override fun create(account: Account): AccountCreated {
        data[account.id] = account
        return AccountCreated(account.id, account.investor.cpf.digits)
    }

    override fun existsByCpf(cpf: Cpf) = data.values.any { it.investor.cpf == cpf }
    override fun findById(id: AccountId): Account? = data[id]

    override fun update(account: Account) {
        data[account.id] = account
    }
}
