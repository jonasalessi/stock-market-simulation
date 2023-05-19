package org.broker.application.order.handler

import com.trendyol.kediatr.NotificationHandler
import io.quarkus.runtime.Startup
import org.broker.application.order.ports.input.OrderCreatedEvent
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
@Startup
class SendOrderToSockExchange: NotificationHandler<OrderCreatedEvent> {
    override suspend fun handle(notification: OrderCreatedEvent) {
        TODO("Not yet implemented")
    }
}