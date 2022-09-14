package org.broker.domain.investor.repository

import org.broker.domain.investor.entity.Investor
import org.broker.domain.investor.vo.Cpf

interface InvestorRepository {
    fun save(investor: Investor)
    fun existsByCpf(cpf: Cpf): Boolean
}