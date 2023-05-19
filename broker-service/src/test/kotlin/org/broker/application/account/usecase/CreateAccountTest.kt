package org.broker.application.account.usecase

import org.broker.application.account.fake.AccountEventPublisherMem
import org.broker.application.account.fake.AccountRepositoryMem
import org.broker.application.account.ports.input.CreateAccountCommand
import org.broker.domain.account.exception.CpfDuplicatedException
import org.broker.domain.account.vo.Cpf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.shared.domain.event.account.AccountCreated
import java.time.LocalDate


class CreateAccountTest {

    private val command = CreateAccountCommand(
        name = "Jonas Alessi",
        birthday = LocalDate.of(1990, 2, 14),
        city = "Florianópolis",
        country = "Brazil",
        cpf = "797.512.620-97"
    )
    private lateinit var investorAccountEventEmitter: AccountEventPublisherMem
    private lateinit var repository: AccountRepositoryMem
    private lateinit var createAccount: CreateAccount

    @BeforeEach
    fun setup() {
        investorAccountEventEmitter = AccountEventPublisherMem()
        repository = AccountRepositoryMem()
        createAccount = CreateAccount(investorAccountEventEmitter, repository)
    }

    @Test
    fun `should create the event AccountCreated when a new investor account is created`() {
        createAccount.handle(command)

        assertEquals(1, investorAccountEventEmitter.events.size) { "Should generate an event" }
        val accountCreated = investorAccountEventEmitter.events.first() as AccountCreated
        assertNotNull(accountCreated.accountId) { "New accountId should be created" }
    }

    @Test
    fun `should persist a new account when CPF 79751262097 account does not exist`() {
        createAccount.handle(command)

        assertEquals(1, repository.data.size) {"Should save the new investor with cpf ${command.cpf}"}
        val investor = repository.data.values.first()
        assertEquals(investor.investor.cpf, Cpf(command.cpf))
    }

    @Test
    fun `should not accept a duplicated account by cpf`() {
        createAccount.handle(command)

        assertThrows<CpfDuplicatedException> { createAccount.handle(command) }
    }
}