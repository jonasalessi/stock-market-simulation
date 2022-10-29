package org.broker.application.order.ports.output

import org.shared.domain.vo.AccountId
import java.math.BigDecimal

interface UserAccountGateway {
    fun getAccountBalance(accountId: AccountId): BigDecimal?
}