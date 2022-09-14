package org.broker

import com.trendyol.kediatr.CommandBus
import org.broker.application.order.ports.input.command.CreateOrderCommand
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello")
class GreetingResource(
    private val commandBus: CommandBus
) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = commandBus.executeCommand(CreateOrderCommand(10))

}