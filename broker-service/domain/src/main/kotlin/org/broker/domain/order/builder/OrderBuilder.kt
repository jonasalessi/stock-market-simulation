package org.broker.domain.order.builder

import org.broker.domain.order.entity.Order
import org.broker.domain.order.entity.OrderBuyable
import org.broker.domain.order.entity.OrderSellable
import org.shared.domain.entity.Share
import org.shared.domain.vo.OrderId
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@DslMarker
annotation class OrderDsl

class SellableBuilder {
    @OrderDsl
    var share: Share = Share.EMPTY

    @OrderDsl
    var myShareBalance: Int = 0
}

class BuyableBuilder {
    @OrderDsl
    var share: Share = Share.EMPTY

    @OrderDsl
    var withBalance: BigDecimal = BigDecimal.ZERO
}

class OrderBuilder {
    @OrderDsl
    var inTradeClock: ZonedDateTime? = null
    private var buildOrder: () -> Order? = { null }

    @OrderDsl
    fun buy(setup: BuyableBuilder.() -> Unit) =
        BuyableBuilder().apply(setup)
            .also {
                buildOrder = { OrderBuyable(OrderId(UUID.randomUUID()), inTradeClock!!, it.share, it.withBalance) }
            }

    @OrderDsl
    fun sell(setup: SellableBuilder.() -> Unit) =
        SellableBuilder().apply(setup)
            .also {
                buildOrder = { OrderSellable(OrderId(UUID.randomUUID()), inTradeClock!!, it.share, it.myShareBalance) }
            }

    fun build() = buildOrder() ?: throw IllegalAccessException("buy {} or sell {} should be called!")
}

fun newOrder(setup: OrderBuilder.() -> Unit) = OrderBuilder().apply(setup).build()
