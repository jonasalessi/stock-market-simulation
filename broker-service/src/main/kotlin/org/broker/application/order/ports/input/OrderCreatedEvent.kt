package org.broker.application.order.ports.input

import com.trendyol.kediatr.Notification
import org.shared.domain.event.order.OrderCreated

data class OrderCreatedEvent(val event: OrderCreated): Notification