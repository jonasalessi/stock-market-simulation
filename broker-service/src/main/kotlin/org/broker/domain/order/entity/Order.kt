package org.broker.domain.order.entity

import org.broker.domain.order.exception.MarketClosedException
import org.broker.domain.order.exception.OrderMinimumException
import org.shared.domain.entity.AggregateRoot
import org.shared.domain.entity.Share
import org.shared.domain.entity.ShareCategory
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.OrderId
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.ZonedDateTime

abstract class Order internal constructor(
    override val id: OrderId,
    private val accountId: AccountId,
    private val inTradeClock: ZonedDateTime,
    private val share: Share
) : AggregateRoot<OrderId>() {

    var total: BigDecimal? = null
        private set
    var price: BigDecimal? = null
        private set
    var quantity: Int? = null
        private set
    var status: OrderStatus? = null
        private set

    init {
        checkIfMarketIsOpen()
    }

    private fun checkIfMarketIsOpen() {
        if (inTradeClock.hour * 100 + inTradeClock.minute !in 1000..1700) throw MarketClosedException()
    }

    abstract fun calculateTrade(price: BigDecimal, quantity: Int): BigDecimal

    fun trade(quantity: Int, price: BigDecimal) {
        validateQuantity(quantity)
        this.total = calculateTrade(price, quantity)
        this.price = price
        this.quantity = quantity
        this.status = OrderStatus.OPEN
    }

    protected fun calculateTotalWithFee(price: BigDecimal, quantity: Int, fee: BigDecimal): BigDecimal {
        val totalWithoutFee = price.multiply(quantity.toBigDecimal())
        return totalWithoutFee.add(totalWithoutFee.multiply(fee)).setScale(2, RoundingMode.CEILING)
    }

    private fun validateQuantity(quantity: Int) {
        if (share.category == ShareCategory.FRACTIONAL && quantity < 1) {
            throw OrderMinimumException("$quantity is not acceptable for fractional shares")
        }
        if (share.category == ShareCategory.INTEGRAL && (quantity % 100 != 0 || quantity < 1)) {
            throw OrderMinimumException("$quantity is not acceptable for integral shares")
        }
    }
}

enum class OrderStatus {
    OPEN
}