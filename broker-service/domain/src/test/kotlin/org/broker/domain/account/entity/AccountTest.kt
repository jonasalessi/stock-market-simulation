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
    fun `should accept only the account status active after wait for deposit`() {
        val account = createAccount()
        assertEquals(AccountStatus.WAITING_BROKER_CHECKING, account.status)
        val ex = assertThrows<AccountFlowStatusException> { account.updateStatus(AccountStatus.ACTIVE) }
        assertEquals("Before approve it should wait for deposit", ex.message)
        account.updateStatus(AccountStatus.WAITING_DEPOSIT)
        account.updateStatus(AccountStatus.ACTIVE)
        assertEquals(AccountStatus.ACTIVE, account.status)
    }

    @Test
    fun `should accept reject account in waiting broker checking stage`() {
        val accountWaitingBroker = createAccount()
        assertEquals(AccountStatus.WAITING_BROKER_CHECKING, accountWaitingBroker.status)
        accountWaitingBroker.updateStatus(AccountStatus.REJECTED)
        assertEquals(AccountStatus.REJECTED, accountWaitingBroker.status)
    }

    @Test
    fun `should not accept reject account when is not waiting broker checking stage`() {
        val accountWaitingBroker = createAccount()
        assertEquals(AccountStatus.WAITING_BROKER_CHECKING, accountWaitingBroker.status)
        accountWaitingBroker.updateStatus(AccountStatus.WAITING_DEPOSIT)
        val rej1 = assertThrows<AccountFlowStatusException> { accountWaitingBroker.updateStatus(AccountStatus.REJECTED) }
        assertEquals("Reject the account is only allowed in document verification stage", rej1.message)
        accountWaitingBroker.updateStatus(AccountStatus.ACTIVE)
        val rej2 = assertThrows<AccountFlowStatusException> { accountWaitingBroker.updateStatus(AccountStatus.REJECTED) }
        assertEquals("Reject the account is only allowed in document verification stage", rej2.message)
    }

    private fun createAccount(birthday: LocalDate = LocalDate.of(1990, 2, 14)): Account {
        val account = Account.create(
            name = "Jonas Alessi",
            birthday = birthday,
            city = "Florianópolis",
            country = "Brazil",
            cpf = Cpf("797.512.620-97")
        )
        return account
    }

}