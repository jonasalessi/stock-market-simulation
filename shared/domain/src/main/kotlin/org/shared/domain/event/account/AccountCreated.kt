package org.shared.domain.event.account

import org.shared.domain.vo.AccountId

data class AccountCreated(val accountId: AccountId, val cpf: String)