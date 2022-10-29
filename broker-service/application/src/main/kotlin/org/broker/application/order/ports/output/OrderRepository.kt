package org.broker.application.order.ports.output

import org.broker.domain.order.entity.Order

interface OrderRepository {
    fun save(order: Order)
}