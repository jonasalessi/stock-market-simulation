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

    fun approve() {
        status = AccountStatus.WAITING_DEPOSIT
    }

    fun reject() {
        if (status != AccountStatus.WAITING_BROKER_CHECKING) {
            throw AccountFlowStatusException("Reject the account is only allowed in document verification stage")
        }
        status = AccountStatus.REJECTED
    }

    fun active() {
        if (status != AccountStatus.WAITING_DEPOSIT) {
            throw AccountFlowStatusException("Before approve it should wait for deposit")
        }
        status = AccountStatus.ACTIVE
    }

    companion object {
        fun build(withName: String, withBirthday: LocalDate, withCity: String, withCountry: String, withCpf: Cpf) =
            Account(
                id = AccountId(UUID.randomUUID()),
                investor = Investor(
                    id = InvestorId(UUID.randomUUID()),
                    name = withName,
                    birthday = withBirthday,
                    city = withCity,
                    country = withCountry,
                    cpf = withCpf
                )
            )
    }
}

