package org.broker.infra.http

import com.trendyol.kediatr.Mediator
import org.broker.application.order.ports.input.BuyOrderCommand
import org.broker.application.order.ports.input.SellOrderCommand
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/orders")
class OrderCommandRest(
    private val mediator: Mediator
) {


    @PostMapping("sell")
    suspend fun placeSellOrder(command: SellOrderCommand) = mediator.send(command)

    @PostMapping("buy")
    suspend fun placeBuyOrder(command: BuyOrderCommand) = mediator.send(command)
}