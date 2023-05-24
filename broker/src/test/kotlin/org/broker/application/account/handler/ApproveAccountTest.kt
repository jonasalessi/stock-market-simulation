package org.broker.application.account.handler

import org.broker.application.account.ports.input.AccountApprovedEvent
import org.broker.infra.fakedb.account.AccountRepositoryMem
import org.broker.domain.account.entity.Account
import org.broker.domain.account.exception.AccountNotFoundException
import org.broker.domain.account.vo.AccountStatus
import org.broker.domain.account.vo.Cpf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.shared.domain.event.account.AccountApproved
import org.shared.domain.vo.AccountId
import java.time.LocalDate
import java.util.*

class ApproveAccountTest {

    private lateinit var approveAccount: ApproveAccount
    private lateinit var repository: AccountRepositoryMem

    @BeforeEach
    fun setup() {
        repository = AccountRepositoryMem()
        approveAccount = ApproveAccount(repository)
    }

    @Test
    suspend fun `should return an exception AccountNotFoundException when the account does not exist`() {
        val accountId = AccountId(UUID.fromString("43519dc2-aca0-40d2-b006-6cc64903be71"))
        val notification = AccountApprovedEvent((AccountApproved(accountId)))
        val ex = assertThrows<AccountNotFoundException> {
            approveAccount.handle(notification)
        }
        assertEquals("Account id 43519dc2-aca0-40d2-b006-6cc64903be71 not found!", ex.message)
    }

    @Test
    suspend fun `should update the account status to waiting deposit when the account is approved`() {
        val account = Account.build(
            withName = "Jonas Alessi",
            withBirthday = LocalDate.of(1990, 2, 14),
            withCity = "Florianópolis",
            withCountry = "Brazil",
            withCpf = Cpf("797.512.620-97")
        )
        val accountId = account.id
        repository.create(account)
        approveAccount.handle(AccountApprovedEvent(AccountApproved(accountId)))
        assertEquals(AccountStatus.WAITING_DEPOSIT, repository.findById(accountId)?.status)
    }
}