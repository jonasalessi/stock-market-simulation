package org.broker.domain.order.entity

import org.broker.domain.order.builder.newOrder
import org.broker.domain.order.exception.InsufficientBalanceException
import org.broker.domain.order.exception.InsufficientShareBalanceException
import org.broker.domain.order.exception.OrderMinimumException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.shared.domain.entity.Share
import org.shared.domain.entity.ShareCategory.FRACTIONAL
import org.shared.domain.entity.ShareCategory.INTEGRAL
import org.shared.domain.vo.ShareId
import java.math.BigDecimal
import java.math.BigDecimal.TEN
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


class OrderTest {
    private val openTime = ZonedDateTime.of(
        LocalDateTime.of(2022, 10, 1, 12, 0, 0),
        ZoneId.of("UTC")
    )

    @Test
    fun `should accept a buy only when have the balance equivalent to the total amount plus 2 percent`() {
        val stock = Share(id = ShareId("XX3F"), category = FRACTIONAL)
        val orderWithoutBalance = newOrder {
            buy {
                share = stock
                withBalance = TEN
            }
            inTradeClock = openTime
        }

        val orderWithBalance = newOrder {
            buy {
                share = stock
                withBalance = BigDecimal(12)
            }
            inTradeClock = openTime
        }

        orderWithBalance.trade(price = TEN, quantity = 1)

        val ex = assertThrows<InsufficientBalanceException> { orderWithoutBalance.trade(1, BigDecimal("12")) }
        assertEquals("It's necessary minimum R$ 14.40 in you account balance", ex.message)
    }

    @Test
    fun `should accept order with minimum 1 share for fractional ticker`() {
        val order = newOrder {
            inTradeClock = openTime
            buy {
                share = Share(
                    id = ShareId("XX3F"),
                    category = FRACTIONAL
                )
                withBalance = BigDecimal("999222")
            }
        }

        for (quantity in -2..0) {
            val ex = assertThrows<OrderMinimumException> {
                order.trade(quantity, TEN)
            }
            assertEquals("$quantity is not acceptable for fractional shares", ex.message)
        }

        for (quantity in 1..200 step 2) order.trade(quantity, TEN)
    }

    @Test
    fun `should accept order with minimum 100 and chunks of 100 shares for integral ticker`() {
        val order = newOrder {
            inTradeClock = openTime
            sell {
                share = Share(
                    id = ShareId("XX3F"),
                    category = INTEGRAL
                )
                myShareBalance = 2000
            }
        }
        for (quantity in -2..99) {
            val ex = assertThrows<OrderMinimumException>("Quantity $quantity") {
                order.trade(quantity, TEN)
            }
            assertEquals("$quantity is not acceptable for integral shares", ex.message)
        }

        for (quantity in 100..1000 step 100) order.trade(quantity, TEN)

    }

    @Test
    fun `should sell only when have the minimum quantity of company shares`() {
        val order = newOrder {
            inTradeClock = openTime
            sell {
                myShareBalance = 10
                share = Share(id = ShareId("XX3F"), category = FRACTIONAL)
            }
        }
        order.trade(10, TEN)
        val ex = assertThrows<InsufficientShareBalanceException> { order.trade(11, TEN) }
        assertEquals("You are trying to sell 11 shares but you have 10", ex.message)
    }

    @ParameterizedTest
    @MethodSource("closedMarket")
    fun `should not allow create an order after 5pm or before 10am`(utcTime: LocalDateTime) {
        /* val now = ZonedDateTime.of(utcTime, ZoneId.of("UTC"))
         val ex = assertThrows<MarketClosedException> { Order.create(balance = TEN, now = now) }
         assertEquals("The market works only between 10am and 5pm", ex.message)*/
    }

    @ParameterizedTest
    @MethodSource("openedMarket")
    fun `should allow only create an order between 10am and 5pm from Brazil time zone`(utcTime: LocalDateTime) {
        /* val now = ZonedDateTime.of(utcTime, ZoneId.of("UTC"))
         Order.create(balance = TEN, now = now)*/
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