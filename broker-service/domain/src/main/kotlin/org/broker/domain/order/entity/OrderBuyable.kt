package org.broker.domain.order.entity

import org.broker.domain.order.exception.InsufficientBalanceException
import org.shared.domain.entity.Share
import org.shared.domain.vo.OrderId
import java.math.BigDecimal
import java.time.ZonedDateTime

class OrderBuyable(
    id: OrderId,
    inTradeClock: ZonedDateTime,
    share: Share,
    private val userBalance: BigDecimal
) : Order(id, inTradeClock, share) {

    override fun calculateTrade(price: BigDecimal, quantity: Int): BigDecimal {
        val total = calculateTotalWithFee(price, quantity, PURCHASE_FEE)
        if (total > userBalance) throw InsufficientBalanceException(total)
        return total
    }

    companion object {
        val PURCHASE_FEE = BigDecimal("0.20")
    }
}