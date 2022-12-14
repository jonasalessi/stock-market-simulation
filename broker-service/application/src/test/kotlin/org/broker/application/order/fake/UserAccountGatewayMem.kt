package org.broker.application.order.fake

import org.broker.application.order.ports.output.UserAccountGateway
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.ShareId
import java.math.BigDecimal

class UserAccountGatewayMem : UserAccountGateway {
    private val accountBalance = mutableMapOf<AccountId, BigDecimal>()
    private val shareQuantity = mutableMapOf<String, Int>()

    override fun getAccountBalance(accountId: AccountId): BigDecimal? = accountBalance[accountId]

    override fun getAccountShareQuantity(accountId: AccountId, shareId: ShareId): Int? =
        shareQuantity[accountId.toString() + shareId.toString()]

    fun saveShareQuantity(accountId: AccountId, shareId: ShareId, quantity: Int) {
        shareQuantity[accountId.toString() + shareId.toString()] = quantity
    }

    fun saveBalance(accountId: AccountId, balance: BigDecimal) {
        accountBalance[accountId] = balance
    }
}