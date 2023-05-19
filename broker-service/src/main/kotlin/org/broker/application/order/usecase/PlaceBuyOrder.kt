package org.broker.application.order.usecase

import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import org.broker.application.order.ports.input.BuyOrderCommand
import org.broker.application.order.ports.output.OrderEventEmitter
import org.broker.application.order.ports.output.TraderClock
import org.broker.application.order.ports.output.UserAccountGateway
import org.broker.application.order.service.OrderShareService
import org.broker.domain.account.exception.AccountNotFoundException
import org.broker.domain.order.builder.newOrder
import org.shared.domain.event.order.OrderCreated
import org.shared.domain.event.order.OrderType
import org.shared.domain.vo.AccountId
import java.math.BigDecimal
import jakarta.enterprise.context.ApplicationScoped


@ApplicationScoped
@Startup
internal class PlaceBuyOrder(
    private val orderShareService: OrderShareService,
    private val clock: TraderClock,
    private val userAccount: UserAccountGateway,
    private val eventEmitter: OrderEventEmitter
) : CommandHandler<BuyOrderCommand> {

    override suspend fun handle(command: BuyOrderCommand) {
        val (shareId, price, quantity, accountId) = command
        val companyShare = orderShareService.findShareById(shareId)
        val financialBalance = getAccountBalance(accountId)
        val order = newOrder {
            inTradeClock = clock.now()
            account = accountId
            buy {
                share = companyShare
                withBalance = financialBalance
            }
        }
        order.trade(quantity, price)
        orderShareService.saveOrder(order)
        eventEmitter.emitOrderCreated(
            OrderCreated(
                orderId = order.id,
                accountId = accountId,
                price = price,
                quantity = quantity,
                shareId = shareId,
                type = OrderType.BUY
            )
        )
    }

    private fun getAccountBalance(accountId: AccountId): BigDecimal =
        userAccount.getAccountBalance(accountId) ?: throw AccountNotFoundException(accountId)

}