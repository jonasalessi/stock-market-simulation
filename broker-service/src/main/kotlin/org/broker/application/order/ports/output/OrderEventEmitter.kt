package org.broker.application.order.ports.output

import org.shared.domain.event.order.OrderCreated

interface OrderEventEmitter {

    fun emitOrderCreated(event: OrderCreated)
}