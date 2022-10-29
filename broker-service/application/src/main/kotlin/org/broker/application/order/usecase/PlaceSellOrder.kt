package org.broker.application.order.usecase

import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import org.broker.application.order.ports.input.BuyOrderCommand
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
@Startup
private class PlaceSellOrder : CommandHandler<BuyOrderCommand> {

    override fun handle(command: BuyOrderCommand) {

    }

}