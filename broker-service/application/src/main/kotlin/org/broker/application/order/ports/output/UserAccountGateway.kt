package org.broker.application.order.ports.output

import org.shared.domain.vo.AccountId
import org.shared.domain.vo.ShareId
import java.math.BigDecimal

interface UserAccountGateway {
    fun getAccountBalance(accountId: AccountId): BigDecimal?
    fun getAccountShareQuantity(accountId: AccountId, shareId: ShareId): Int?
}