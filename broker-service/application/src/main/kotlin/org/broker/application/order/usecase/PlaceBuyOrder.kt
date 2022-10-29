package org.broker.application.order.usecase

import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import org.broker.application.order.ports.input.BuyOrderCommand
import org.broker.application.order.ports.output.*
import org.broker.domain.account.exception.AccountNotFoundException
import org.broker.domain.order.builder.newOrder
import org.broker.domain.order.entity.Order
import org.shared.domain.entity.Share
import org.shared.domain.event.order.OrderCreated
import org.shared.domain.event.order.OrderType
import org.shared.domain.exception.ShareNotFoundException
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.ShareId
import java.math.BigDecimal
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
@Startup
internal class PlaceBuyOrder(
    private val shareRepository: ShareRepository,
    private val orderRepository: OrderRepository,
    private val clock: TraderClock,
    private val userAccount: UserAccountGateway,
    private val eventEmitter: OrderEventEmitter
) : CommandHandler<BuyOrderCommand> {

    override fun handle(command: BuyOrderCommand) {
        val (shareId, price, quantity, accountId) = command
        val companyShare = findShareById(shareId)
        val financialBalance = getAccountBalance(accountId)
        val order = newOrder {
            inTradeClock = clock.now()
            buy {
                share = companyShare
                withBalance = financialBalance
            }
        }
        order.trade(quantity, price)
        orderRepository.save(order)
        eventEmitter.emitOrderCreated(OrderCreated(
            orderId = order.id,
            accountId = accountId,
            price = price,
            quantity = quantity,
            shareId = shareId,
            type = OrderType.BUY
        ))
    }

    private fun getAccountBalance(accountId: AccountId): BigDecimal =
        userAccount.getAccountBalance(accountId) ?: throw AccountNotFoundException(accountId)

    private fun findShareById(shareId: ShareId): Share =
        shareRepository.findById(shareId) ?: throw ShareNotFoundException(shareId)

}