package org.broker.application.order.usecase

import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import org.broker.application.order.ports.input.command.CreateOrderCommand
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
@Startup
private class CreateOrderHandler : CommandHandler<CreateOrderCommand> {

    override fun handle(command: CreateOrderCommand) {
        println("Created order to customer ${command.customerId}")
    }

}