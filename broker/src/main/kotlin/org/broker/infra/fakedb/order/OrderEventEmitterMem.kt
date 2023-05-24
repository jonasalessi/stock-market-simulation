package org.broker.infra.fakedb.order

import org.broker.application.order.ports.output.OrderEventEmitter
import org.shared.domain.event.order.OrderCreated
import org.shared.domain.vo.OrderId
import org.springframework.stereotype.Component

@Component
class OrderEventEmitterMem : OrderEventEmitter {
    val data = mutableMapOf<OrderId, OrderCreated>()

    override fun emitOrderCreated(event: OrderCreated) {
        data[event.orderId] = event
    }
}