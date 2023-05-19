package org.broker.application.order.usecase

import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import org.broker.application.order.ports.input.SellOrderCommand
import org.broker.application.order.ports.output.*
import org.broker.application.order.service.OrderShareService
import org.broker.domain.account.exception.AccountShareNotFoundException
import org.broker.domain.order.builder.newOrder
import org.shared.domain.entity.Share
import org.shared.domain.event.order.OrderCreated
import org.shared.domain.event.order.OrderType
import org.shared.domain.exception.ShareNotFoundException
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.ShareId
import jakarta.enterprise.context.ApplicationScoped


@ApplicationScoped
@Startup
internal class PlaceSellOrder(
    private val orderShareService: OrderShareService,
    private val clock: TraderClock,
    private val userAccount: UserAccountGateway,
    private val eventEmitter: OrderEventEmitter
) : CommandHandler<SellOrderCommand> {

    override suspend fun handle(command: SellOrderCommand) {
        // TODO add security check if the account is from the user that is requesting
        val (shareId, price, quantity, accountId) = command
        val companyShare = orderShareService.findShareById(shareId)
        val accountShareBalance = getAccountShareBalance(accountId, shareId)
        val order = newOrder {
            inTradeClock = clock.now()
            account = accountId
            sell {
                myShareBalance = accountShareBalance
                share = companyShare
            }
        }
        order.trade(quantity, price)
        orderShareService.saveOrder(order)
        eventEmitter.emitOrderCreated(
            OrderCreated(
                shareId = shareId,
                orderId = order.id,
                type = OrderType.SELL,
                quantity = quantity,
                price = price,
                accountId = accountId
            )
        )
    }

    private fun getAccountShareBalance(accountId: AccountId, shareId: ShareId): Int =
        userAccount.getAccountShareQuantity(accountId, shareId) ?: throw AccountShareNotFoundException(shareId)

}