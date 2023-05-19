package org.broker.application.order.fake

import org.broker.application.order.ports.output.OrderEventEmitter
import org.shared.domain.event.order.OrderCreated
import org.shared.domain.vo.OrderId

class OrderEventEmitterMem: OrderEventEmitter {
    val data = mutableMapOf<OrderId, OrderCreated>()

    override fun emitOrderCreated(event: OrderCreated) {
        data[event.orderId] = event
    }
}