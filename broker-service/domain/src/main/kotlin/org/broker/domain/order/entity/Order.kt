package org.broker.domain.order.entity

import org.shared.domain.entity.AggregateRoot
import org.shared.domain.vo.OrderId

class Order private constructor(override val id: OrderId) : AggregateRoot<OrderId>() {

    companion object {
        fun create()
    }
}