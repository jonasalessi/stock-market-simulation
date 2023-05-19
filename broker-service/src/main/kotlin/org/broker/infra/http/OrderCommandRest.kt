package org.broker.infra.http

import com.trendyol.kediatr.Mediator
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import org.broker.application.order.ports.input.BuyOrderCommand
import org.broker.application.order.ports.input.SellOrderCommand


@Path("/orders")
class OrderCommandRest(
    private val mediator: Mediator
) {


    @Path("sell")
    @POST
    suspend fun placeSellOrder(command: SellOrderCommand) = mediator.send(command)

    @Path("buy")
    @POST
    suspend fun placeBuyOrder(command: BuyOrderCommand) = mediator.send(command)
}