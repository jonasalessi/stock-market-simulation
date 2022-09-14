package org.broker.domain.investor.entity

import org.broker.domain.investor.vo.AccountStatus
import org.broker.domain.investor.vo.Cpf
import org.shared.domain.entity.Entity
import org.shared.domain.vo.AccountId

class Account(override val id: AccountId, val cpf: Cpf, val status: AccountStatus): Entity<AccountId>() {

}

