package org.shared.domain.event.order

import org.shared.domain.vo.AccountId
import org.shared.domain.vo.OrderId
import org.shared.domain.vo.ShareId
import java.math.BigDecimal

data class OrderCreated(
    val orderId: OrderId,
    val shareId: ShareId,
    val price: BigDecimal,
    val quantity: Int,
    val type: OrderType,
    val accountId: AccountId
)

enum class OrderType {
    BUY,
    SELL
}