package org.broker.domain.investor.entity

import org.broker.domain.investor.exception.Under18YearsOldException
import org.broker.domain.investor.vo.AccountStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.YearMonth

class InvestorTest {

    @Test
    fun `should create an investor account with initial status as waiting deposit`() {
        val investor = Investor.create(
            name = "Jonas Alessi",
            birthday = LocalDate.of(1990, 2, 14),
            city = "Tallinn",
            country = "Estonia",
            cpf = "797.512.620-97"
        )
        assertEquals(AccountStatus.WAITING_DEPOSIT, investor.account.status)
    }

    @Test
    fun `should not accept under 18 years old`() {
        val exception = assertThrows<Under18YearsOldException> {
            Investor.create(
                name = "Jonas Alessi",
                birthday = LocalDate.of(YearMonth.now().year - 15, 2, 14),
                city = "Tallinn",
                country = "Estonia",
                cpf = "797.512.620-97"
            )
        }
        assertEquals("Under 18 years old are not allowed to open an account!", exception.message)
    }

}