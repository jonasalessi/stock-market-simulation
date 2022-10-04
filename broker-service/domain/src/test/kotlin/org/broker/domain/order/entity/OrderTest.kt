package org.broker.domain.order.entity

import org.broker.domain.order.exception.MarketClosedException
import org.broker.domain.order.exception.OrderMinimumException
import org.broker.domain.order.exception.WithoutBalanceException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.shared.domain.entity.Share
import org.shared.domain.entity.ShareCategory.FRACTIONAL
import org.shared.domain.entity.ShareCategory.INTEGRAL
import java.math.BigDecimal
import java.math.BigDecimal.TEN
import java.math.BigDecimal.ZERO
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class OrderTest {
    private val openTime = ZonedDateTime.of(
        LocalDateTime.of(2022, 10, 1, 12, 0, 0),
        ZoneId.of("UTC")
    )

    @Test
    fun `should buy only when have the balance equivalent to the total amount plus 2 percent`() {
        val orderWithoutBalance = Order.create(balance = TEN, now = openTime)
        val orderWithBalance = Order.create(balance = BigDecimal("12"), now = openTime)
        val share = Share(id = "XX3F", price = TEN, category = FRACTIONAL)
        orderWithBalance.buy(share, 1)
        val ex = assertThrows<WithoutBalanceException> { orderWithoutBalance.buy(share, 1) }
        assertEquals("It's necessary minimum R$ 10.21 in you account balance", ex.message)
    }

    @ParameterizedTest
    @MethodSource("closedMarket")
    fun `should not allow create an order after 5pm or before 10am`(utcTime: LocalDateTime) {
        val now = ZonedDateTime.of(utcTime, ZoneId.of("UTC"))
        val ex = assertThrows<MarketClosedException> { Order.create(balance = TEN, now = now) }
        assertEquals("The market works only between 10am and 5pm", ex.message)
    }

    @ParameterizedTest
    @MethodSource("openedMarket")
    fun `should allow only create an order between 10am and 5pm from Brazil time zone`(utcTime: LocalDateTime) {
        val now = ZonedDateTime.of(utcTime, ZoneId.of("UTC"))
        Order.create(balance = TEN, now = now)
    }

    @Test
    fun `should accept buy minimum 1 share for fractional ticker`() {
        val order = Order.create(balance = TEN, now = openTime)
        listOf(-1, 0).forEach { quantity ->
            val ex = assertThrows<OrderMinimumException> {
                order.buy(
                    share = Share(
                        id = "XX3F",
                        price = ZERO,
                        category = FRACTIONAL
                    ), quantity = quantity
                )
            }
            assertEquals("$quantity is not acceptable for fractional shares", ex.message)
        }
        order.buy(share = Share(id = "XX3F", price = ZERO, category = FRACTIONAL), quantity = 1)
    }

    @Test
    fun `should accept buy minimum 100 and chunks of 100 shares for integral ticker`() {
        val order = Order.create(balance = BigDecimal("999999"), now = openTime)
        listOf(50, 150).forEach { quantity ->
            val ex = assertThrows<OrderMinimumException> {
                order.buy(
                    share = Share(
                        id = "XX3F",
                        price = TEN,
                        category = INTEGRAL
                    ), quantity = quantity
                )
            }
            assertEquals("$quantity is not acceptable for integral shares", ex.message)
        }
        order.buy(share = Share(id = "XX3F", price = TEN, category = INTEGRAL), quantity = 100)
    }

    companion object {
        @JvmStatic
        fun openedMarket() = (10..17).map {
            LocalDateTime.of(2022, 10, 1, it, 0, 0)
        }

        @JvmStatic
        fun closedMarket() = listOf(
            LocalDateTime.of(2022, 10, 1, 8, 0, 0),
            LocalDateTime.of(2022, 10, 1, 9, 59, 0),
            LocalDateTime.of(2022, 10, 1, 17, 1, 0),
            LocalDateTime.of(2022, 10, 1, 18, 0, 0)
        )
    }
}