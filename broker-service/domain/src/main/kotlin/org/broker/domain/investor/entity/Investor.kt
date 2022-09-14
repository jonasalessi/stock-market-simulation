package org.broker.domain.investor.entity

import org.broker.domain.investor.exception.Under18YearsOldException
import org.broker.domain.investor.vo.AccountStatus
import org.broker.domain.investor.vo.Cpf
import org.shared.domain.entity.AggregateRoot
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.InvestorId
import java.time.LocalDate
import java.time.Period
import java.util.*

class Investor private constructor(
    override val id: InvestorId,
    val name: String,
    val birthday: LocalDate,
    val city: String,
    val country: String,
    val account: Account
): AggregateRoot<InvestorId>() {

    init {
        if (Period.between(birthday, LocalDate.now()).years < 18) {
            throw Under18YearsOldException()
        }
    }

    companion object {
        fun create(name: String, birthday: LocalDate, city: String, country: String, cpf: Cpf) =
            Investor(
                id = InvestorId(UUID.randomUUID()),
                name = name,
                birthday = birthday,
                city = city,
                country = country,
                account = Account(
                    id = AccountId(UUID.randomUUID()),
                    cpf = cpf,
                    status = AccountStatus.WAITING_BROKER_CHECKING
                )
            )
    }


}