package org.broker.application.account.handler

import org.broker.application.account.ports.input.AccountApprovedNotification
import org.broker.application.investor.usecase.AccountRepositoryMem
import org.broker.domain.account.entity.Account
import org.broker.domain.account.exception.AccountNotFoundException
import org.broker.domain.account.vo.AccountStatus
import org.broker.domain.account.vo.Cpf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.shared.domain.event.AccountApproved
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
    fun `should return an exception with message Account id 43519dc2-aca0-40d2-b006-6cc64903be71 not found!`() {
        val accountId = AccountId(UUID.fromString("43519dc2-aca0-40d2-b006-6cc64903be71"))
        val notification = AccountApprovedNotification((AccountApproved(accountId)))
        val ex = assertThrows<AccountNotFoundException> {
            approveAccount.handle(notification)
        }
        assertEquals("Account id 43519dc2-aca0-40d2-b006-6cc64903be71 not found!", ex.message)
    }

    @Test
    fun `should update the account status to Approved`() {
        val account = Account.create(
            name = "Jonas Alessi",
            birthday = LocalDate.of(1990, 2, 14),
            city = "Florianópolis",
            country = "Brazil",
            cpf = Cpf("797.512.620-97")
        )
        val accountId = account.id
        repository.save(account)
        approveAccount.handle(AccountApprovedNotification(AccountApproved(accountId)))
        assertEquals(AccountStatus.ACTIVE, repository.findById(accountId)?.status)
    }
}