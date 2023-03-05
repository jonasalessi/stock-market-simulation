package org.broker.domain.account.entity

import org.broker.domain.account.exception.AccountFlowStatusException
import org.broker.domain.account.exception.Under18YearsOldException
import org.broker.domain.account.vo.AccountStatus
import org.broker.domain.account.vo.Cpf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.YearMonth

class AccountTest {

    @Test
    fun `should create an account with initial status as waiting broker checking`() {
        val account = createAccount()
        assertEquals(AccountStatus.WAITING_BROKER_CHECKING, account.status)
    }

    @Test
    fun `should not accept under 18 years old`() {
        val exception = assertThrows<Under18YearsOldException> {
            createAccount(birthday = LocalDate.of(YearMonth.now().year - 15, 2, 14))
        }
        assertEquals("Under 18 years old are not allowed to open an account!", exception.message)
    }

    @Test
    fun `should active account only after account approval passed by waiting for deposit phase`() {
        val account = createAccount()
        val ex = assertThrows<AccountFlowStatusException> { account.active() }
        assertEquals("Before approve it should wait for deposit", ex.message)
        account.approve()
        assertEquals(AccountStatus.WAITING_DEPOSIT, account.status)
        account.active()
        assertEquals(AccountStatus.ACTIVE, account.status)
    }

    @Test
    fun `should reject account when is in waiting broker checking phase`() {
        val accountWaitingBroker = createAccount()
        accountWaitingBroker.reject()
        assertEquals(AccountStatus.REJECTED, accountWaitingBroker.status)
    }

    @Test
    fun `should not reject account when it was approved or activated`() {
        val accountWaitingBroker = createAccount()
        accountWaitingBroker.approve()
        val cannotReject = assertThrows<AccountFlowStatusException> { accountWaitingBroker.reject() }
        accountWaitingBroker.active()
        val rejectEx = assertThrows<AccountFlowStatusException> { accountWaitingBroker.reject() }
        assertEquals("Reject the account is only allowed in document verification stage", cannotReject.message)
        assertEquals("Reject the account is only allowed in document verification stage", rejectEx.message)
    }

    private fun createAccount(birthday: LocalDate = LocalDate.of(1990, 2, 14)): Account =
        Account.build(
            withName = "Jonas Alessi",
            withBirthday = birthday,
            withCity = "Florianópolis",
            withCountry = "Brazil",
            withCpf = Cpf("797.512.620-97")
        )


}