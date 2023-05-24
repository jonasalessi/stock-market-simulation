package org.broker.application.order.handler

import com.trendyol.kediatr.NotificationHandler
import org.broker.application.order.ports.input.OrderCreatedEvent
import org.springframework.stereotype.Component

@Component
class SendOrderToSockExchange : NotificationHandler<OrderCreatedEvent> {
    override suspend fun handle(notification: OrderCreatedEvent) {
        TODO("Not yet implemented")
    }
}