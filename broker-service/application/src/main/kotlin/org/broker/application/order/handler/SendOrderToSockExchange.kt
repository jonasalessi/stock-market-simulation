package org.broker.application.order.handler

import com.trendyol.kediatr.NotificationHandler
import io.quarkus.runtime.Startup
import org.broker.application.order.ports.input.OrderCreatedEvent
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@Startup
class SendOrderToSockExchange: NotificationHandler<OrderCreatedEvent> {
    override fun handle(notification: OrderCreatedEvent) {
        TODO("Not yet implemented")
    }
}