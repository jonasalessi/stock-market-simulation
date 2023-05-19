package org.broker.application.order.usecase

import org.broker.infra.fakedb.order.OrderEventEmitterMem
import org.broker.infra.fakedb.order.OrderRepositoryMem
import org.broker.infra.fakedb.order.ShareGatewayMem
import org.broker.infra.fakedb.order.UserAccountGatewayMem
import org.broker.application.order.ports.input.BuyOrderCommand
import org.broker.application.order.service.OrderShareService
import org.broker.application.order.stub.TraderClockStub
import org.broker.domain.account.exception.AccountNotFoundException
import org.broker.domain.order.entity.OrderStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.shared.domain.entity.Share
import org.shared.domain.entity.ShareCategory
import org.shared.domain.event.order.OrderCreated
import org.shared.domain.event.order.OrderType
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.ShareId
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class PlaceBuyOrderTest {
    private lateinit var orderRepositoryMem: OrderRepositoryMem
    private lateinit var shareRepositoryMem: ShareGatewayMem
    private lateinit var userAccountGatewayMem: UserAccountGatewayMem
    private lateinit var eventEmitter: OrderEventEmitterMem
    private lateinit var placeOrder: PlaceBuyOrder
    private val openTime = ZonedDateTime.of(
        LocalDateTime.of(2022, 10, 1, 12, 0, 0),
        ZoneId.of("UTC")
    )
    private val jacksonAccountId = AccountId(UUID.fromString("1a052bd7-1d37-48a5-a0f5-4ad7493ea629"))

    @BeforeEach
    fun setup() {
        orderRepositoryMem = OrderRepositoryMem()
        shareRepositoryMem = ShareGatewayMem()
        eventEmitter = OrderEventEmitterMem()
        userAccountGatewayMem = UserAccountGatewayMem()
        placeOrder = PlaceBuyOrder(
            clock = TraderClockStub(openTime),
            orderShareService = OrderShareService(shareRepositoryMem, orderRepositoryMem),
            userAccount = userAccountGatewayMem,
            eventEmitter = eventEmitter
        )
    }

    @Test
    suspend fun `should return an exception AccountNotFoundException when the Jackson account does not exists`() {
        persistIntegralShare()
        val ex = assertThrows<AccountNotFoundException> {
            placeOrder.handle(
                BuyOrderCommand(
                    shareId = ShareId("GGBR4"),
                    price = BigDecimal("22"),
                    quantity = 100,
                    accountId = jacksonAccountId
                )
            )
        }
        assertThat(ex.message, equalTo("Account id 1a052bd7-1d37-48a5-a0f5-4ad7493ea629 not found!"))
    }

    @Test
    suspend fun `should publish an opened event when order saved and traded`() {
        persistIntegralShare()
        persistJacksonAccountBalance()
        val command = BuyOrderCommand(
            shareId = ShareId("GGBR4"),
            price = BigDecimal("22"),
            quantity = 100,
            accountId = jacksonAccountId
        )

        placeOrder.handle(command)

        val event = eventEmitter.data.values.firstOrNull()
        val order = orderRepositoryMem.data.values.firstOrNull()
        val orderCreated = OrderCreated(
            shareId = command.shareId,
            orderId = order!!.id,
            type = OrderType.BUY,
            quantity = command.quantity,
            price = command.price,
            accountId = command.accountId
        )
        assertThat(order.status, equalTo(OrderStatus.OPEN))
        assertThat(event, equalTo(orderCreated))
    }

    private fun persistJacksonAccountBalance() {
        userAccountGatewayMem.saveBalance(jacksonAccountId, BigDecimal(9_999))
    }

    private fun persistIntegralShare(shareSymbol: String = "GGBR4") {
        shareRepositoryMem.save(Share(ShareId(shareSymbol), ShareCategory.INTEGRAL))
    }
}