package org.broker.domain.account.entity

import org.broker.domain.account.exception.Under18YearsOldException
import org.broker.domain.account.vo.Cpf
import org.shared.domain.entity.Entity
import org.shared.domain.vo.InvestorId
import java.time.LocalDate
import java.time.Period

class Investor internal constructor(
    override val id: InvestorId,
    val name: String,
    val birthday: LocalDate,
    val city: String,
    val country: String,
    val cpf: Cpf
) : Entity<InvestorId>() {

    init {
        if (Period.between(birthday, LocalDate.now()).years < 18) {
            throw Under18YearsOldException()
        }
    }


}