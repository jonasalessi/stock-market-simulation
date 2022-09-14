package org.broker.application.investor.usecase

import org.broker.application.investor.ports.input.CreateInvestorAccountCommand
import org.broker.domain.investor.entity.Investor
import org.broker.domain.investor.exception.CpfDuplicatedException
import org.broker.domain.investor.repository.InvestorRepository
import org.broker.domain.investor.vo.Cpf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.shared.domain.event.AccountCreated
import java.time.LocalDate


class CreateInvestorAccountTest {

    private val command = CreateInvestorAccountCommand(
        name = "Jonas Alessi",
        birthday = LocalDate.of(1990, 2, 14),
        city = "Florianópolis",
        country = "Brazil",
        cpf = "797.512.620-97"
    )
    private lateinit var investorAccountEventEmitter: InvestorAccountEventEmitterMem
    private lateinit var repository: InvestorRepositoryMem
    private lateinit var createInvestorAccount: CreateInvestorAccount

    @BeforeEach
    fun setup() {
        investorAccountEventEmitter = InvestorAccountEventEmitterMem()
        repository = InvestorRepositoryMem()
        createInvestorAccount = CreateInvestorAccount(investorAccountEventEmitter, repository)
    }

    @Test
    fun `should create the event AccountCreated when a new investor account is created`() {
        createInvestorAccount.handle(command)

        assertEquals(1, investorAccountEventEmitter.events.size) { "Should generate an event" }
        val accountCreated = investorAccountEventEmitter.events[0] as AccountCreated
        assertNotNull(accountCreated.accountId) { "New accountId should be created" }
    }

    @Test
    fun `should persist the aggregate root into the repository`() {
        createInvestorAccount.handle(command)

        assertEquals(1, repository.data.size) {"Should save the new investor with cpf ${command.cpf}"}
        val investor = repository.data[0]
        assertEquals(investor.account.cpf, Cpf(command.cpf))
    }

    @Test
    fun `should not accept a duplicated account by cpf`() {
        createInvestorAccount.handle(command)

        assertThrows<CpfDuplicatedException> { createInvestorAccount.handle(command) }
    }
}