package org.broker.application.order.ports.input.command

import com.trendyol.kediatr.Command

data class CreateOrderCommand(
    val customerId: Long
) : Command
