package org.broker.application.investor.usecase

import org.broker.domain.investor.entity.Investor
import org.broker.domain.investor.repository.InvestorRepository
import org.broker.domain.investor.vo.Cpf

class InvestorRepositoryMem : InvestorRepository {
    val data = mutableListOf<Investor>()

    override fun save(investor: Investor) {
        data += investor
    }

    override fun existsByCpf(cpf: Cpf) = data.any { it.account.cpf ==  cpf }
}
