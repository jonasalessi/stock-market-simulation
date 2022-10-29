package org.broker.application.order.fake

import org.broker.application.order.ports.output.UserAccountGateway
import org.shared.domain.vo.AccountId
import java.math.BigDecimal

class UserAccountGatewayMem: UserAccountGateway {
    val data = mutableMapOf<AccountId, BigDecimal>()

    override fun getAccountBalance(accountId: AccountId): BigDecimal?  = data[accountId]

    fun saveBalance(accountId: AccountId, balance: BigDecimal) {
        data[accountId] = balance
    }
}