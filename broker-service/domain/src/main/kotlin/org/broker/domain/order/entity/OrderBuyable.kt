package org.broker.domain.order.entity

import org.broker.domain.order.exception.InsufficientBalanceException
import org.shared.domain.entity.Share
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.OrderId
import java.math.BigDecimal
import java.time.ZonedDateTime

class OrderBuyable(
    id: OrderId,
    accountId: AccountId,
    inTradeClock: ZonedDateTime,
    share: Share,
    private val cashBalance: BigDecimal
) : Order(id, accountId, inTradeClock, share) {

    override fun calculateTrade(price: BigDecimal, quantity: Int): BigDecimal {
        val total = calculateTotalWithFee(price, quantity, PURCHASE_FEE)
        if (total > cashBalance) throw InsufficientBalanceException(total)
        return total
    }

    companion object {
        val PURCHASE_FEE = BigDecimal("0.20")
    }
}