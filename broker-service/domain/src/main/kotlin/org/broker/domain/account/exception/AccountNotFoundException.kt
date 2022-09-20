package org.broker.domain.account.exception

import org.shared.domain.vo.AccountId

class AccountNotFoundException(id: AccountId): Exception("Account id ${id.uuid} not found!")