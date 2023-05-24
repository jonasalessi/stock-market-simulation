package org.broker.infra.fakedb.order

import org.broker.application.order.ports.output.OrderRepository
import org.broker.domain.order.entity.Order
import org.shared.domain.vo.OrderId
import org.springframework.stereotype.Component

@Component
class OrderRepositoryMem : OrderRepository {
    val data = mutableMapOf<OrderId, Order>()

    override fun save(order: Order) {
        data[order.id] = order
    }
}