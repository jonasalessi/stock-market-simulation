package org.broker.domain.order.entity

import org.broker.domain.order.exception.MarketClosedException
import org.broker.domain.order.exception.OrderMinimumException
import org.broker.domain.order.exception.WithoutBalanceException
import org.shared.domain.entity.AggregateRoot
import org.shared.domain.entity.Share
import org.shared.domain.entity.ShareCategory
import org.shared.domain.vo.OrderId
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class Order private constructor(override val id: OrderId, val balance: BigDecimal) : AggregateRoot<OrderId>() {
    private var total: BigDecimal? = null
    private var share: Share? = null
    private var quantity: Int = 0

    fun buy(share: Share, quantity: Int) {
        validateQuantity(share, quantity)
        val totalWithoutFee = share.price.multiply(quantity.toBigDecimal())
        val amount = totalWithoutFee.add(totalWithoutFee.multiply(FEE)).setScale(2, RoundingMode.CEILING)
        if (amount > balance) throw WithoutBalanceException(amount)
        this.total = amount
        this.share = share
        this.quantity = quantity
    }

    private fun validateQuantity(share: Share, quantity: Int) {
        if (share.category == ShareCategory.FRACTIONAL && quantity < 1) {
            throw OrderMinimumException("$quantity is not acceptable for fractional shares")
        }
        if (share.category == ShareCategory.INTEGRAL && quantity % 100 != 0) {
            throw OrderMinimumException("$quantity is not acceptable for integral shares")
        }
    }

    companion object {
        private val FEE = BigDecimal(0.02)
        fun create(
            balance: BigDecimal,
            now: ZonedDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
        ): Order {
            if (now.hour * 100 + now.minute !in 1000..1700) throw MarketClosedException()
            return Order(
                id = OrderId(UUID.randomUUID()),
                balance = balance
            )
        }
    }
}