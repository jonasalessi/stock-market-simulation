package org.broker.application.order.usecase

import org.broker.application.order.fake.OrderEventEmitterMem
import org.broker.application.order.fake.OrderRepositoryMem
import org.broker.application.order.fake.ShareRepositoryMem
import org.broker.application.order.fake.UserAccountGatewayMem
import org.broker.application.order.ports.input.SellOrderCommand
import org.broker.application.order.stub.TraderClockStub
import org.broker.domain.account.exception.AccountNotFoundException
import org.broker.domain.account.exception.AccountShareNotFoundException
import org.broker.domain.order.entity.OrderStatus
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.shared.domain.entity.Share
import org.shared.domain.entity.ShareCategory
import org.shared.domain.event.order.OrderType
import org.shared.domain.exception.ShareNotFoundException
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.ShareId
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class PlaceSellOrderTest {
    private lateinit var orderRepositoryMem: OrderRepositoryMem
    private lateinit var shareRepositoryMem: ShareRepositoryMem
    private lateinit var userAccountGatewayMem: UserAccountGatewayMem
    private lateinit var eventEmitter: OrderEventEmitterMem
    private lateinit var placeOrder: PlaceSellOrder
    private val openTime = ZonedDateTime.of(
        LocalDateTime.of(2022, 10, 1, 12, 0, 0),
        ZoneId.of("UTC")
    )
    private val jacksonAccountId = AccountId(UUID.fromString("1a052bd7-1d37-48a5-a0f5-4ad7493ea629"))

    @BeforeEach
    fun setup() {
        orderRepositoryMem = OrderRepositoryMem()
        shareRepositoryMem = ShareRepositoryMem()
        eventEmitter = OrderEventEmitterMem()
        userAccountGatewayMem = UserAccountGatewayMem()
        placeOrder = PlaceSellOrder(
            clock = TraderClockStub(openTime),
            orderRepository = orderRepositoryMem,
            shareRepository = shareRepositoryMem,
            userAccount = userAccountGatewayMem,
            eventEmitter = eventEmitter
        )
    }

    @Test
    fun `should return an exception ShareNotFoundException when the GGBR4 does not exists`() {
        val ex = assertThrows<ShareNotFoundException> {
            placeOrder.handle(
                SellOrderCommand(
                    shareId = ShareId("GGBR4"),
                    price = BigDecimal("22"),
                    quantity = 100,
                    accountId = jacksonAccountId
                )
            )
        }
        assertThat(ex.message, equalTo("Share GGBR4 not found!"))
    }

    @Test
    fun `should return an exception AccountShareNotFoundException when the Jackson account does not exists`() {
        persistIntegralShare()
        val ex = assertThrows<AccountShareNotFoundException> {
            placeOrder.handle(
                SellOrderCommand(
                    shareId = ShareId("GGBR4"),
                    price = BigDecimal("22"),
                    quantity = 100,
                    accountId = jacksonAccountId
                )
            )
        }
        assertThat(
            ex.message,
            equalTo("You don't have shares of GGBR4!")
        )
    }
    @Test
    fun `should publish an opened event when order saved and traded`() {
        persistIntegralShare()
        persistJacksonAccountBalance()
        val share = ShareId("GGBR4")
        userAccountGatewayMem.saveShareQuantity(jacksonAccountId, share, 100)
        val command = SellOrderCommand(
            shareId = share,
            price = BigDecimal("22"),
            quantity = 100,
            accountId = jacksonAccountId
        )

        placeOrder.handle(command)

        val event = eventEmitter.data.values.firstOrNull()
        val order = orderRepositoryMem.data.values.firstOrNull()
        assertThat(order?.status, equalTo(OrderStatus.OPEN))
        assertThat(event?.accountId, equalTo(command.accountId))
        assertThat(event?.type, equalTo(OrderType.SELL))
        assertThat(event?.price, equalTo(order?.price))
        assertThat(event?.shareId, equalTo(command.shareId))
        assertThat(event?.quantity, equalTo(order?.quantity))
    }
    private fun persistJacksonAccountBalance() {
        userAccountGatewayMem.saveBalance(jacksonAccountId, BigDecimal(9_999))
    }

    private fun persistIntegralShare(shareSymbol: String = "GGBR4") {
        shareRepositoryMem.save(Share(ShareId(shareSymbol), ShareCategory.INTEGRAL))
    }
}