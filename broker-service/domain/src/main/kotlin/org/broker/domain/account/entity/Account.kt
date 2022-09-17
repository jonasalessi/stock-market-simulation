package org.broker.domain.account.entity

import org.broker.domain.account.exception.AccountFlowStatusException
import org.broker.domain.account.vo.AccountStatus
import org.broker.domain.account.vo.Cpf
import org.shared.domain.entity.AggregateRoot
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.InvestorId
import java.time.LocalDate
import java.util.*

class Account private constructor(
    override val id: AccountId,
    val investor: Investor
) : AggregateRoot<AccountId>() {

    var status: AccountStatus = AccountStatus.WAITING_BROKER_CHECKING
        private set

    fun updateStatus(newStatus: AccountStatus) {
        if (newStatus == AccountStatus.REJECTED && status != AccountStatus.WAITING_BROKER_CHECKING)
            throw AccountFlowStatusException("Reject the account is only allowed in document verification stage")
        if (newStatus == AccountStatus.ACTIVE && status != AccountStatus.WAITING_DEPOSIT) {
            throw AccountFlowStatusException("Before approve it should wait for deposit")
        }
        status = newStatus
    }

    companion object {
        fun create(name: String, birthday: LocalDate, city: String, country: String, cpf: Cpf) =
            Account(
                id = AccountId(UUID.randomUUID()),
                investor = Investor(
                    id = InvestorId(UUID.randomUUID()),
                    name = name,
                    birthday = birthday,
                    city = city,
                    country = country,
                    cpf = cpf
                )
            )
    }
}

