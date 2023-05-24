package org.broker.domain.order.entity

import org.broker.domain.order.exception.InsufficientShareBalanceException
import org.shared.domain.entity.Share
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.OrderId
import java.math.BigDecimal
import java.time.ZonedDateTime

class OrderSellable(
    id: OrderId,
    accountId: AccountId,
    inTradeClock: ZonedDateTime,
    share: Share,
    private val accountShareBalance: Int
) : Order(id, accountId, inTradeClock, share) {

    override fun calculateTrade(price: BigDecimal, quantity: Int): BigDecimal {
        if (accountShareBalance < quantity) throw InsufficientShareBalanceException(quantity, accountShareBalance)
        return calculateTotalWithFee(price, quantity, SELLING_FEE)
    }

    companion object {
        val SELLING_FEE = BigDecimal("0.50")
    }
}